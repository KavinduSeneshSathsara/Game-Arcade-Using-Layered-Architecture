package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.EmployeeBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.EmployeeDAO;
import lk.ijse.GameCafe.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.GameCafe.dto.EmployeeDto;
import lk.ijse.GameCafe.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public String totalEmployeeCount() throws SQLException, ClassNotFoundException {
        return employeeDAO.totalEmployeeCount();
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
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
        return employeeDAO.save(new Employee(
                dto.getEmpId(),
                dto.getEmpName(),
                dto.getEmpContactNum(),
                dto.getEmpSalary(),
                dto.getEmpAddress()
        ));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        List<Employee> employees = employeeDAO.getAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees){
            employeeDtos.add(new EmployeeDto(
              employee.getEmpId(),
              employee.getEmpName(),
              employee.getEmpContactNum(),
              employee.getEmpSalary(),
              employee.getEmpAddress()
            ));
        }
        return employeeDtos;
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        EmployeeDto employeeDto = new EmployeeDto(
                employee.getEmpId(),
                employee.getEmpName(),
                employee.getEmpContactNum(),
                employee.getEmpSalary(),
                employee.getEmpAddress()
                );
        return employeeDto;
    }
}
