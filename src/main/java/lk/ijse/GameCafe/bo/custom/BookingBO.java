package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.entity.Booking;

import java.sql.Date;
import java.sql.SQLException;
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
}
