package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.EmployeeDAO;
import lk.ijse.GameCafe.dto.EmployeeDto;
import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.entity.Employee;

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
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET emp_name = ?, emp_contact_num = ?, emp_salary = ?, emp_address = ? WHERE emp_id = ?",
            entity.getEmpName(),
            entity.getEmpContactNum(),
            entity.getEmpSalary(),
            entity.getEmpAddress(),
            entity.getEmpId()
        );
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT CONCAT('E', LPAD(IFNULL(MAX(SUBSTRING(emp_id, 2)), 0) + 1, 4, '0')) FROM employee");

        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VAlUES (?,?,?,?,?)",
                entity.getEmpId(),
                entity.getEmpName(),
                entity.getEmpContactNum(),
                entity.getEmpSalary(),
                entity.getEmpAddress()
        );
    }

    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");

        List<Employee> list = new ArrayList<>();

        while (rst.next()){
            Employee employee = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            list.add(employee);
        }
        return list;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE emp_id = ?", id);
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE emp_id = ?", id);

        if (rst.next()){
            return new Employee(
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
