package lk.ijse.GameCafe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    private String paymentId;
    private String bookingId;
    private Date date;
    private Time time;
    private double amount;
}
