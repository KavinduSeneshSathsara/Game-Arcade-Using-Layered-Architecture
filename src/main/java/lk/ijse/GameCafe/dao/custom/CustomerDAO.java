package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    String generateNewCustomerId() throws SQLException, ClassNotFoundException;

    List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

    CustomerDto SearchCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
}
