package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.entity.Customer;

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
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('C', LPAD(IFNULL(MAX(SUBSTRING(cus_id, 2)), 0) + 1, 4, '0')) FROM customer");

        if (rst.next()) {
            return rst.getString(1);
        }

        return null;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?)",
                entity.getCusId(),
                entity.getCusContactNum(),
                entity.getCusEmail(),
                entity.getCusName(),
                entity.getCusAddress()
        );
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");

        List<Customer> list = new ArrayList<>();

        while (rst.next()){

            Customer entity = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            list.add(entity);
        }

        return list;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET contact_num = ?, email = ?, cus_name = ?, customer_address = ? WHERE cus_id = ?",
                entity.getCusContactNum(),
                entity.getCusEmail(),
                entity.getCusName(),
                entity.getCusAddress(),
                entity.getCusId()
        );
    }


    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE cus_id = ?" , (id + ""));

        if(rst.next()){
            return new Customer(
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
    public Customer getCustomer(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE contact_num=?", s);

        if (rst.next()) {
            return new Customer(
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
