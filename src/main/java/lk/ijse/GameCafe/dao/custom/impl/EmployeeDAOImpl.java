package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.EmployeeDAO;
import lk.ijse.GameCafe.dto.EmployeeDto;
import lk.ijse.GameCafe.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public String totalEmployeeCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) AS EmployeeCount FROM Employee");

        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET emp_name = ?, emp_contact_num = ?, emp_salary = ?, emp_address = ? WHERE emp_id = ?",
            dto.getEmpName(),
            dto.getEmpContactNum(),
            dto.getEmpSalary(),
            dto.getEmpAddress(),
            dto.getEmpId()
        );
    }

    @Override
    public String generateNewEmpId() throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT CONCAT('E', LPAD(IFNULL(MAX(SUBSTRING(emp_id, 2)), 0) + 1, 4, '0')) FROM employee");
//        ResultSet rst = SQLUtil.execute("SELECT id FROM employee ORDER BY emp_id DESC LIMIT 1;");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VAlUES (?,?,?,?,?)",
                dto.getEmpId(),
                dto.getEmpName(),
                dto.getEmpContactNum(),
                dto.getEmpSalary(),
                dto.getEmpAddress()
        );
    }

    @Override
    public List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");

        List<EmployeeDto> list = new ArrayList<>();

        while (rst.next()){
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            list.add(employeeDto);
        }
        return list;
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE emp_id = ?", id);
    }

    @Override
    public EmployeeDto SearchModel(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE emp_id = ?", id);

        if (rst.next()){
            return new EmployeeDto(
              rst.getString(1),
              rst.getString(2),
              rst.getString(3),
              rst.getString(4),
              rst.getString(5)
            );
        }
        return null;
    }
}
