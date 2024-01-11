package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dao.SuperDAO;
import lk.ijse.GameCafe.dto.BookingDetailsDto;
import lk.ijse.GameCafe.entity.BookingDetails;

import java.sql.SQLException;
import java.util.List;

public interface BookingDetailDAO extends CrudDAO<BookingDetailsDto> {
    boolean saveDetails(List<BookingDetailsDto> collect) throws SQLException, ClassNotFoundException;
}
