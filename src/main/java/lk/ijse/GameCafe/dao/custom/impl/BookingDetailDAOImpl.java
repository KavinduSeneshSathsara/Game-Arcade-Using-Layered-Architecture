package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.BookingDetailDAO;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.util.SQLUtil;

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
}
