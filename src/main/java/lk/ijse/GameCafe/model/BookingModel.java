package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.PaymentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public BookingDto getBookingData(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM booking WHERE booking_id=?");
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return new BookingDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getTime(4),
                    resultSet.getTime(5),
                    resultSet.getTime(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8)
            );
        }
        return null;
    }

    public boolean updateStatus(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("update booking set booking_status = 'Paid' where booking_id=?");
        pstm.setString(1,id);

        return pstm.executeUpdate() != 0;
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

    public ArrayList<BookingDto> getAllBookings(String psId, Date date) throws SQLException {
        Connection connection = DbConnection.getInstance( ).getConnection( );

        String sql = "select b.start_time, b.end_time\n" +
                "        from booking b \n" +
                "        join booking_Details bd on b.booking_id = bd.booking_id\n" +
                "        where bd.play_station_id = ?\n" +
                "        and b.booking_date = ?";

        PreparedStatement ps = connection.prepareStatement( sql );

        ps.setString( 1, psId );
        ps.setDate( 2, date );

        ResultSet rst = ps.executeQuery( );

        ArrayList<BookingDto> list = new ArrayList<>();

        while ( rst.next() ) {
            list.add( new BookingDto(
                    null,
                    null,
                    null,
                    null,
                    rst.getTime( 1 ),
                    rst.getTime( 2 ),
                    null,
                    null
            ) );
        }

        return list;
    }
    public String totalBookingCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS BookingCount FROM booking";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();


        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public List<BookingDto> getAllBooking() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM booking";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        List<BookingDto> list = new ArrayList<>();

        while (resultSet.next()) {
            BookingDto dto = new BookingDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getTime(4),
                    resultSet.getTime(5),
                    resultSet.getTime(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8));
            list.add(dto);

        }
        return list;
    }
}
