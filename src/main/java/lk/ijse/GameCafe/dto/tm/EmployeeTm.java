package lk.ijse.GameCafe.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeTm {
    private String empId;
    private String empName;
    private String empContactNum;
    private String empSalary;
    private String empAddress;
}
