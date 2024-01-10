package lk.ijse.GameCafe.view.tdm.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingTm {
    private String bookingId;
    private String cus_id;
    private Date bookingDate;
    private Time bookingTime;
    private Time startTime;
    private Time endTime;
    private String status;
    private Double total;
}
