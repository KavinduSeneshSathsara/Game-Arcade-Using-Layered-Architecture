package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BookingDAO {
    String generateNextId() throws SQLException, ClassNotFoundException;

    boolean saveBooking(BookingDto notPaid) throws SQLException, ClassNotFoundException;

    List<BookingDto> getAllBookings(String value, Date date) throws SQLException, ClassNotFoundException;

    boolean updateStatus(String s) throws SQLException, ClassNotFoundException;

    BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException;

    List<BookingDto> getAllBooking() throws SQLException, ClassNotFoundException;

    String totalBookingCount() throws SQLException, ClassNotFoundException;
}
