package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.tm.CustomerTm;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<CustomerDto> {
    CustomerDto getCustomer(String s) throws SQLException, ClassNotFoundException;
    String totalCustomerCount() throws SQLException, ClassNotFoundException;
}
