package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.BookingBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dao.custom.BookingDetailDAO;
import lk.ijse.GameCafe.dao.custom.QuaryDAO;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class BookingBOImpl implements BookingBO {

    BookingDetailDAO bookingDetailDAO = (BookingDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKINGDETAIL);
    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);
    QuaryDAO quaryDAO = (QuaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

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

    @Override
    public List<BookingDto> getAllBookings(String value, Date date) throws SQLException, ClassNotFoundException {
        return quaryDAO.getAllBookings(value, date);
    }
}
