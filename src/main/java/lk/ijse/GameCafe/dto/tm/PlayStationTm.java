package lk.ijse.GameCafe.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayStationTm {
        private String playStationId;
        private String playStationNumber;
        private String status;
        private double rate;
}
