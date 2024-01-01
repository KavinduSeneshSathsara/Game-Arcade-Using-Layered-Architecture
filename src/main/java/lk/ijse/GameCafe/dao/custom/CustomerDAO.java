package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.SQLException;

public interface CustomerDAO {

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    String generateNewCustomerId() throws SQLException, ClassNotFoundException;
}
