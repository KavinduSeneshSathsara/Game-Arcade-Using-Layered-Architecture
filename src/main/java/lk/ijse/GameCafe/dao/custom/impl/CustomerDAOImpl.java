package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cus_id = ?", id);
    }

    @Override
    public boolean save(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?)",
                dto.getCusId(),
                dto.getCusContactNum(),
                dto.getCusEmail(),
                dto.getCusName(),
                dto.getCusAddress()
        );
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('C', LPAD(IFNULL(MAX(SUBSTRING(cus_id, 2)), 0) + 1, 4, '0')) FROM customer");

        if (rst.next()) {
            return rst.getString(1);
        }

        return null;
    }

    @Override
    public List<CustomerDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");

        List<CustomerDto> list = new ArrayList<>();

        while (rst.next()){

            CustomerDto  customerDto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            list.add(customerDto);
        }

        return list;
    }

    @Override
    public CustomerDto search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE cus_id = ?" , (id + ""));

        if(rst.next()){
            return new CustomerDto(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public boolean update(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET contact_num = ?, email = ?, cus_name = ?, customer_address = ? WHERE cus_id = ?",
                dto.getCusContactNum(),
                dto.getCusEmail(),
                dto.getCusName(),
                dto.getCusAddress(),
                dto.getCusId()
        );
    }

    @Override
    public CustomerDto getCustomer(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE contact_num=?", s);

        if (rst.next()) {
            return new CustomerDto(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public String totalCustomerCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) AS CustomerCount FROM customer");

        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }
}
