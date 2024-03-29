package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.EmployeeDto;
import lk.ijse.GameCafe.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {
    String totalEmployeeCount() throws SQLException, ClassNotFoundException;
}
