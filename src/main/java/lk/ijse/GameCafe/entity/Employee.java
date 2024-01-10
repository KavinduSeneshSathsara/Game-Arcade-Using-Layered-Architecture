package lk.ijse.GameCafe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private String empId;
    private String empName;
    private String empContactNum;
    private String empSalary;
    private String empAddress;
}
