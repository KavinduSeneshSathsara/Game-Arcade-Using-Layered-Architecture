package lk.ijse.GameCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayStationDto {
    private String PlayStationId;
    private String playStationNumber;
    private String Status;
    private double rate;
}
