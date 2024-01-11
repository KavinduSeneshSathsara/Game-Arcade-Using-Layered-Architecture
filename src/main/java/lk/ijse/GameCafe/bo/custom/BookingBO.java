package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface BookingBO extends SuperBO {
    String generateBookingId() throws SQLException, ClassNotFoundException;

    boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException;
    boolean updateStatus(String s) throws SQLException, ClassNotFoundException;
    BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException;
    List<BookingDto> getAllBookings() throws SQLException, ClassNotFoundException;
    String totalBookingCount() throws SQLException, ClassNotFoundException;
    boolean saveDetails(List<BookingDetailsDto> detailList) throws SQLException, ClassNotFoundException;
    List<BookingDto> getAllBookings(String value, Date date) throws SQLException, ClassNotFoundException;
    boolean bookAndSave(Date nowDate, Time nowTime, Time startTime, Time endTime, String notPaid, double v, CustomerDto customerdto, List<BookingDetailsDto> collect) throws SQLException, ClassNotFoundException;
}
