package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingModel {
    public boolean saveBooking(BookingDto bookingDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO booking VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,bookingDto.getBookingId());
        pstm.setString(2,bookingDto.getCus_id());
        pstm.setDate(3,bookingDto.getBookingDate());
        pstm.setTime(4,bookingDto.getBookingTime());
        pstm.setTime(5,bookingDto.getStartTime());
        pstm.setTime(6,bookingDto.getEndTime());
        pstm.setString(7,bookingDto.getStatus());
        pstm.setDouble(8,bookingDto.getTotal());
        return pstm.executeUpdate()>0;
    }
    public String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM booking ORDER BY booking_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        String  current = null;

        if (resultSet.next()) {
            current = resultSet.getString(1);
            return splitId(current);
        }
        return splitId(null);

    }

    private String splitId(String current) {
        if (current != null) {
            String[] split = current.split("B");
            int id = Integer.parseInt(split[1]);
            id++;
            if (9 >= id && id > 0) return "O00" + id;
            else if (99 >= id && id > 9) return "O0" + id;
            else if (id > 99) return String.valueOf(id);
        }
        return "O001";
    }
}
