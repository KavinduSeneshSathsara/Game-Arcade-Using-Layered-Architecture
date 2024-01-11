package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.CustomerBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(
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
        List<Customer> customers = customerDAO.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers){
            customerDtos.add(new CustomerDto(
                    customer.getCusId(),
                    customer.getCusContactNum(),
                    customer.getCusEmail(),
                    customer.getCusName(),
                    customer.getCusAddress()
            ));
        }
        return customerDtos;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        CustomerDto customerDto = new CustomerDto(
          customer.getCusId(),
          customer.getCusContactNum(),
          customer.getCusEmail(),
          customer.getCusName(),
          customer.getCusAddress()
        );
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(
                dto.getCusId(),
                dto.getCusContactNum(),
                dto.getCusEmail(),
                dto.getCusName(),
                dto.getCusAddress()
        ));
    }

    @Override
    public CustomerDto getCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.getCustomer(s);
        CustomerDto customerDto = new CustomerDto(
                customer.getCusId(),
                customer.getCusContactNum(),
                customer.getCusEmail(),
                customer.getCusName(),
                customer.getCusAddress()
        );
        return customerDto;
    }

    @Override
    public String totalCustomerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.totalCustomerCount();
    }
}
