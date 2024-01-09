package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {
    String totalEmployeeCount() throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;

    String generateNewEmpId() throws SQLException, ClassNotFoundException;

    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;

    List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    EmployeeDto SearchModel(String id) throws SQLException, ClassNotFoundException;
}
