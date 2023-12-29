package lk.ijse.GameCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {

    private String cusId;
    private String cusContactNum;
    private String cusEmail;
    private String cusName;
    private String cusAddress;

}
