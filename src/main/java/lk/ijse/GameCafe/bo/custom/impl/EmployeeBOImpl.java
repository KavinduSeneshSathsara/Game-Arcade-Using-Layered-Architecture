package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.EmployeeBO;
import lk.ijse.GameCafe.dao.custom.EmployeeDAO;
import lk.ijse.GameCafe.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.GameCafe.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    @Override
    public String totalEmployeeCount() throws SQLException, ClassNotFoundException {
        return employeeDAO.totalEmployeeCount();
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new EmployeeDto(
                dto.getEmpId(),
                dto.getEmpName(),
                dto.getEmpContactNum(),
                dto.getEmpSalary(),
                dto.getEmpAddress()
        ));
    }

    @Override
    public String generateEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateId();
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new EmployeeDto(
                dto.getEmpId(),
                dto.getEmpName(),
                dto.getEmpContactNum(),
                dto.getEmpSalary(),
                dto.getEmpAddress()
        ));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.search(id);
    }
}
