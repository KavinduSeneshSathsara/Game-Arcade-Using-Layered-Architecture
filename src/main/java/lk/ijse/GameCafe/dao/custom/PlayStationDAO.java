package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.PlayStationDto;

import java.sql.SQLException;

public interface PlayStationDAO extends CrudDAO<PlayStationDto> {
    double getRate(String station) throws SQLException, ClassNotFoundException;
}
