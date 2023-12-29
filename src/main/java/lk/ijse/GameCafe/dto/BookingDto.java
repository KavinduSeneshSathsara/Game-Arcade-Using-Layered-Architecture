package lk.ijse.GameCafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.function.ToDoubleBiFunction;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    private String bookingId;
    private String cus_id;
    private Date bookingDate;
    private Time bookingTime;
    private Time startTime;
    private Time endTime;
    private String status;
    private Double total;
}
