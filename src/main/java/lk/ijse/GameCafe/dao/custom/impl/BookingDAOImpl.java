package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1;");

        String  current = null;

        if (rst.next()) {
            current = rst.getString(1);
            return splitId(current);
        }
        return splitId(null);
    }

    private String splitId(String current) {
        if (current != null) {
            String[] split = current.split("O");
            int id = Integer.parseInt(split[1]);
            id++;
            if (9 >= id && id > 0) return "O00" + id;
            else if (99 >= id && id > 9) return "O0" + id;
            else if (id > 99) return String.valueOf(id);
        }
        return "O001";
    }

    @Override
    public boolean save(Booking booking) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO booking VALUES (?,?,?,?,?,?,?,?)",
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

    @Override
    public boolean updateStatus(String s) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE booking SET booking_status = 'Paid' WHERE booking_id = ?", s);
    }

    @Override
    public BookingDto getBookingData(String value) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM booking WHERE booking_id = ?", value);

        if (rst.next()){
            return new BookingDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getTime(5),
                    rst.getTime(6),
                    rst.getString(7),
                    rst.getDouble(8)
            );
        }
        return null;
    }

    @Override
    public List<Booking> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM booking");

        List<Booking> list = new ArrayList<>();

        while (rst.next()){
            Booking booking =new Booking(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getTime(5),
                    rst.getTime(6),
                    rst.getString(7),
                    rst.getDouble(8)
            );
            list.add(booking);
        }
        return list;
    }

    @Override
    public String totalBookingCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) AS BookingCount FROM booking");

        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean update(Booking booking) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Booking search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
