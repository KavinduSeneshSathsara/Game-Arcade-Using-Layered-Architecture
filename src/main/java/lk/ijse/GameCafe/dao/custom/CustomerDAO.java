package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    CustomerDto getCustomer(String s) throws SQLException, ClassNotFoundException;
    String totalCustomerCount() throws SQLException, ClassNotFoundException;
}
