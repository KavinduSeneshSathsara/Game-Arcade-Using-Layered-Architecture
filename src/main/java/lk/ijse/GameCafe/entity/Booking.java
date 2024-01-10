package lk.ijse.GameCafe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking {
    private String bookingId;
    private String cus_id;
    private Date bookingDate;
    private Time bookingTime;
    private Time startTime;
    private Time endTime;
    private String status;
    private Double total;
}
