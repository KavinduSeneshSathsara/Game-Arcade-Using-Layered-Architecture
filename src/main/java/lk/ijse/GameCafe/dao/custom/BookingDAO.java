package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.SQLException;

public interface BookingDAO {
    String generateNextId() throws SQLException, ClassNotFoundException;

    boolean saveBooking(BookingDto notPaid) throws SQLException, ClassNotFoundException;
}
