package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public EmployeeDto SearchModel(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();

        EmployeeDto dto = null;

        if (resultSet.next()) {
            String empID = resultSet.getString(1);
            String empName = resultSet.getString(2);
            String empContactNumber = resultSet.getString(3);
            String empSalary = resultSet.getString(4);
            String empAddress = resultSet.getString(5);

            dto = new EmployeeDto(empID, empName, empContactNumber, empSalary, empAddress);
        }
        return dto;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET emp_name = ?, emp_contact_num = ?, emp_salary = ?, emp_address = ? WHERE emp_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1,dto.getEmpName());
        ps.setString(2,dto.getEmpContactNum());
        ps.setString(3,dto.getEmpSalary());
        ps.setString(4,dto.getEmpAddress());
        ps.setString(5, dto.getEmpId());


        return ps.executeUpdate() > 0;
    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE emp_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1,id);

        return ps.executeUpdate() > 0;
    }

    public List<EmployeeDto> getAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()) {
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return empList;
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee VALUES(?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1,dto.getEmpId());
        ps.setString(2,dto.getEmpName());
        ps.setString(3,dto.getEmpContactNum());
        ps.setString(4,dto.getEmpSalary());
        ps.setString(5,dto.getEmpAddress());

        int i = ps.executeUpdate();
        return i> 0;
    }

    public String generateNewEmpId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT CONCAT('E', LPAD(IFNULL(MAX(SUBSTRING(emp_id, 2)), 0) + 1, 4, '0')) FROM employee";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }

            return null; // Return null if something goes wrong
        }
    }
    public String totalEmployeeCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS EmployeeCount FROM Employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();


        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}

