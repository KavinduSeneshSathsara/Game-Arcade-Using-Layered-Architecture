package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.BookingDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface BookingDetailDAO {
    boolean saveDetails(List<BookingDetailsDto> collect) throws SQLException, ClassNotFoundException;
}
