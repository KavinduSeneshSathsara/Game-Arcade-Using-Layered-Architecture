package lk.ijse.GameCafe.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTm {
    private String paymentId;
    private String bookingId;
    private Date date;
    private Time time;
    private double amount;
}
