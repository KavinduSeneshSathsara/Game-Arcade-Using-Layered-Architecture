package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.entity.Booking;

import java.sql.SQLException;

public interface BookingDAO extends CrudDAO<Booking>{
    boolean updateStatus(String s) throws SQLException, ClassNotFoundException;
    Booking getBookingData(String value) throws SQLException, ClassNotFoundException;
    String totalBookingCount() throws SQLException, ClassNotFoundException;
}
