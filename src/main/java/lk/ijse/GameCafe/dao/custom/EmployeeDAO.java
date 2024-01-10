package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.EmployeeDto;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<EmployeeDto> {
    String totalEmployeeCount() throws SQLException, ClassNotFoundException;
}
