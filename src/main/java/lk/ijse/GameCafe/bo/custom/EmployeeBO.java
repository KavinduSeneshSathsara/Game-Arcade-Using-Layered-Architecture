package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    String totalEmployeeCount() throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    String generateEmployeeId() throws SQLException, ClassNotFoundException;
    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException;
}
