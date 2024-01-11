package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.BookingDetailDAO;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.entity.BookingDetails;

import java.sql.SQLException;
import java.util.List;

public class BookingDetailDAOImpl implements BookingDetailDAO{

    @Override
    public boolean saveDetails(List<BookingDetailsDto> detailList) throws SQLException, ClassNotFoundException {
        for (BookingDetailsDto details : detailList) {
            return SQLUtil.execute("INSERT INTO booking_details VALUES (?,?)",
                    details.getBookingId(),
                    details.getPlayStationId()
            );
        }
        return Boolean.parseBoolean(null);
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BookingDetails bookingDetails) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<BookingDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(BookingDetails bookingDetails) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BookingDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
