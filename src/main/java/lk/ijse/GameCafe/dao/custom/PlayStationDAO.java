package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.PlayStationDto;
import lk.ijse.GameCafe.entity.PlayStation;

import java.sql.SQLException;

public interface PlayStationDAO extends CrudDAO<PlayStation> {
    double getRate(String station) throws SQLException, ClassNotFoundException;
}
