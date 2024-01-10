package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.SQLException;

public interface BookingDAO extends CrudDAO<BookingDto> {
    boolean updateStatus(String s) throws SQLException, ClassNotFoundException;
    BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException;
    String totalBookingCount() throws SQLException, ClassNotFoundException;
}
