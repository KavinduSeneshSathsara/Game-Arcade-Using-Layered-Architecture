package lk.ijse.GameCafe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayStation {
    private String PlayStationId;
    private String playStationNumber;
    private String Status;
    private double rate;
}
