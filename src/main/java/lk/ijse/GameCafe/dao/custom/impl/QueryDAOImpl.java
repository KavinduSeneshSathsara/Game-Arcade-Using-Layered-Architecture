package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.dao.custom.QuaryDAO;
import lk.ijse.GameCafe.dto.BookingDto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QuaryDAO {

    @Override
    public List<BookingDto> getAllBookings(String value, Date date) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select b.start_time, b.end_time\n" +
                "        from booking b \n" +
                "        join booking_Details bd on b.booking_id = bd.booking_id\n" +
                "        where bd.play_station_id = ?\n" +
                "        and b.booking_date = ?", value, date);

        List<BookingDto> list = new ArrayList<>();

        while (rst.next()) {
            new BookingDto(
                    null,
                    null,
                    null,
                    null,
                    rst.getTime( 1 ),
                    rst.getTime( 2 ),
                    null,
                    null
            );
        }
        return list;
    }
}
