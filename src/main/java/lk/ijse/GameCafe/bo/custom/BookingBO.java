package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.SQLException;
import java.util.List;

public interface BookingBO {
    String generateBookingId() throws SQLException, ClassNotFoundException;
    boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException;
    boolean updateStatus(String s) throws SQLException, ClassNotFoundException;
    BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException;
    List<BookingDto> getAllBookings() throws SQLException, ClassNotFoundException;
    String totalBookingCount() throws SQLException, ClassNotFoundException;
    boolean saveDetails(List<BookingDetailsDto> detailList) throws SQLException, ClassNotFoundException;
}
