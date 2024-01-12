package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.BookingBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dao.custom.BookingDetailDAO;
import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dao.custom.QuaryDAO;
import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.entity.Booking;
import lk.ijse.GameCafe.entity.Customer;
import lk.ijse.GameCafe.util.TransactionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class BookingBOImpl implements BookingBO {

    BookingDetailDAO bookingDetailDAO = (BookingDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKINGDETAIL);
    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);
    QuaryDAO quaryDAO = (QuaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public boolean bookAndSave(Date nowDate, Time nowTime, Time startTime, Time endTime, String notPaid, double v, CustomerDto customerdto, List<BookingDetailsDto> collect) throws SQLException, ClassNotFoundException {

        try{
            TransactionUtil.startTransaction();

            boolean bookingSaved = bookingDAO.save(new Booking(bookingDAO.generateId(), customerdto.getCusId(), nowDate, nowTime, startTime, endTime, "Not Paid", v));

            if (!bookingSaved) {
                System.err.println("Failed to save booking information.");

                TransactionUtil.rollBack();
                return false;
            }

            boolean bookingDetailSaved = bookingDetailDAO.saveDetails(collect);

            if (!bookingDetailSaved) {
                System.err.println("Failed to save booking details.");

                TransactionUtil.rollBack();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            TransactionUtil.rollBack();
        }finally {
            TransactionUtil.endTransaction();
        }
        return true;
    }

    @Override
    public String generateBookingId() throws SQLException, ClassNotFoundException {
        return bookingDAO.generateId();
    }

    @Override
    public BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException {
        Booking booking = bookingDAO.getBookingData(value);

        if (booking != null) {
            return new BookingDto(
                    booking.getBookingId(),
                    booking.getCus_id(),
                    booking.getBookingDate(),
                    booking.getBookingTime(),
                    booking.getStartTime(),
                    booking.getEndTime(),
                    booking.getStatus(),
                    booking.getTotal()
            );
        }
        return null;
    }

    @Override
    public List<BookingDto> getAllBookings() throws SQLException, ClassNotFoundException {
        List<Booking> bookings = bookingDAO.getAll();
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDtos.add(new BookingDto(
                    booking.getBookingId(),
                    booking.getCus_id(),
                    booking.getBookingDate(),
                    booking.getBookingTime(),
                    booking.getStartTime(),
                    booking.getEndTime(),
                    booking.getStatus(),
                    booking.getTotal()
            ));
        }
        return bookingDtos;
    }

    @Override
    public String totalBookingCount() throws SQLException, ClassNotFoundException {
        return bookingDAO.totalBookingCount();
    }

    @Override
    public List<BookingDto> getAllBookings(String value, Date date) throws SQLException, ClassNotFoundException {
        return quaryDAO.getAllBookings(value, date);
    }
}
