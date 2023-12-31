package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.BookingDetailsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class BookingDetailModel {

    public boolean saveDetails(List<BookingDetailsDto> bookingDetailsDtos) throws SQLException {
        Connection connection = DbConnection.getInstance( ).getConnection( );

        String sql = "insert into booking_Details values (?, ?)";

        PreparedStatement ps = connection.prepareStatement( sql );

        for (BookingDetailsDto dto : bookingDetailsDtos) {
            ps.setString( 1, dto.getBookingId() );
            ps.setString( 2, dto.getPlayStationId() );

            if ( ps.executeUpdate() == 0 ) {
                return false;
            }
        }
        return true;
    }
}
