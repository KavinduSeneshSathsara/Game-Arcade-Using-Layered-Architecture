package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.CustomerBO;
import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new CustomerDto(
                dto.getCusId(),
                dto.getCusContactNum(),
                dto.getCusEmail(),
                dto.getCusName(),
                dto.getCusAddress()
        ));
    }

    @Override
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateId();
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new CustomerDto(
                dto.getCusId(),
                dto.getCusContactNum(),
                dto.getCusEmail(),
                dto.getCusName(),
                dto.getCusAddress()
        ));
    }

    @Override
    public CustomerDto getCustomer(String s) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomer(s);
    }

    @Override
    public String totalCustomerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.totalCustomerCount();
    }
}
