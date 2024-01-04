package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDAOImpl implements BookingDAO {
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
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
    public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO booking VALUES (?,?,?,?,?,?,?,?)",
                bookingDto.getBookingId(),
                bookingDto.getCus_id(),
                bookingDto.getBookingDate(),
                bookingDto.getBookingTime(),
                bookingDto.getStartTime(),
                bookingDto.getEndTime(),
                bookingDto.getStatus(),
                bookingDto.getTotal()
        );
    }
}
