package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.BookingBO;
import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dao.custom.BookingDetailDAO;
import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dao.custom.impl.BookingDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.BookingDetailDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public class BookingBOImpl implements BookingBO {

    BookingDAO bookingDAO = new BookingDAOImpl();
    BookingDetailDAO bookingDetailDAO = new BookingDetailDAOImpl();
    @Override
    public String generateBookingId() throws SQLException, ClassNotFoundException {
        return bookingDAO.generateId();
    }

    @Override
    public boolean saveBooking(BookingDto dto) throws SQLException, ClassNotFoundException {
        return bookingDAO.save(new BookingDto(
                dto.getBookingId(),
                dto.getCus_id(),
                dto.getBookingDate(),
                dto.getBookingTime(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getStatus(),
                dto.getTotal()));
    }

    @Override
    public boolean updateStatus(String s) throws SQLException, ClassNotFoundException {
        return bookingDAO.updateStatus(s);
    }

    @Override
    public BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException {
        return bookingDAO.getBookingData(value);
    }

    @Override
    public List<BookingDto> getAllBookings() throws SQLException, ClassNotFoundException {
        return bookingDAO.getAll();
    }

    @Override
    public String totalBookingCount() throws SQLException, ClassNotFoundException {
        return bookingDAO.totalBookingCount();
    }

    @Override
    public boolean saveDetails(List<BookingDetailsDto> detailList) throws SQLException, ClassNotFoundException {
        return bookingDetailDAO.saveDetails(detailList);
    }
}
