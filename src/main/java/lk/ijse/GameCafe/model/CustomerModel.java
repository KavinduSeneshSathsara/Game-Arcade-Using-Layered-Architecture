package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public static List<CustomerDto> loadAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CustomerDto> cusList = new ArrayList<>();

        while (resultSet.next()) {
            cusList.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return cusList;
    }

    public String generateNewCustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT CONCAT('C', LPAD(IFNULL(MAX(SUBSTRING(cus_id, 2)), 0) + 1, 4, '0')) FROM customer";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }

            return null; // Return null if something goes wrong
        }
    }

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, dto.getCusId());
        ps.setString(2, dto.getCusContactNum());
        ps.setString(3, dto.getCusEmail());
        ps.setString(4, dto.getCusName());
        ps.setString(5, dto.getCusAddress());

        int i = ps.executeUpdate();
        return i > 0;
    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        List<CustomerDto> list = new ArrayList<>();

        while (resultSet.next()) {
            CustomerDto dto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));

            list.add(dto);

        }

        return list;

    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE cus_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, id);

        return ps.executeUpdate() > 0;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET contact_num = ?, email = ?, cus_name = ?, customer_address = ? WHERE cus_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, dto.getCusContactNum());
        ps.setString(2, dto.getCusEmail());
        ps.setString(3, dto.getCusName());
        ps.setString(4, dto.getCusAddress());
        ps.setString(5, dto.getCusId());

        return ps.executeUpdate() > 0;
    }

    public CustomerDto SearchModel(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE cus_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();

        CustomerDto dto = null;

        if (resultSet.next()) {
            String cusId = resultSet.getString(1);
            String cusContactNum = resultSet.getString(2);
            String cusEmail = resultSet.getString(3);
            String cusName = resultSet.getString(4);
            String cusAddress = resultSet.getString(5);

            dto = new CustomerDto(cusId, cusContactNum, cusEmail, cusName, cusAddress);
        }
        return dto;
    }

    public CustomerDto getCustomer(String contact) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE contact_num=?");
        pstm.setString(1,contact);
        ResultSet resultSet = pstm.executeQuery();
        CustomerDto dto =null;
        if (resultSet.next()){
            dto=new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return dto;
    }

}