package lk.ijse.GameCafe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private String cusId;
    private String cusContactNum;
    private String cusEmail;
    private String cusName;
    private String cusAddress;
}
