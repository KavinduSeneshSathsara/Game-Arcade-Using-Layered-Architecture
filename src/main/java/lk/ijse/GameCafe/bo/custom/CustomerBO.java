package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
    String generateCustomerId() throws SQLException, ClassNotFoundException;
    List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
    CustomerDto getCustomer(String s) throws SQLException, ClassNotFoundException;
    String totalCustomerCount() throws SQLException, ClassNotFoundException;
}
