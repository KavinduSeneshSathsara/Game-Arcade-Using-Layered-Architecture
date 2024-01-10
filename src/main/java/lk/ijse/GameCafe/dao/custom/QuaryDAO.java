package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.SuperDAO;
import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface QuaryDAO extends SuperDAO {
    List<BookingDto> getAllBookings(String value, Date date) throws SQLException, ClassNotFoundException;
}
