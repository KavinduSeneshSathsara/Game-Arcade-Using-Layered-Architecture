package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE cus_id = ?", id);
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?)",
                dto.getCusId(),
                dto.getCusContactNum(),
                dto.getCusEmail(),
                dto.getCusName(),
                dto.getCusAddress()
        );
    }

    @Override
    public String generateNewCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('C', LPAD(IFNULL(MAX(SUBSTRING(cus_id, 2)), 0) + 1, 4, '0')) FROM customer");

        if (rst.next()) {
            return rst.getString(1);
        }

        return null;
    }
}