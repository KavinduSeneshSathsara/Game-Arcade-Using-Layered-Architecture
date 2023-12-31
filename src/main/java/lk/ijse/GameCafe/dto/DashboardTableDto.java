package lk.ijse.GameCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DashboardTableDto {
    private String bookingId;
    private String cusId;
    private double total;
}
