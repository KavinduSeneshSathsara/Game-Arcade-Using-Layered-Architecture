package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.PlayStationDto;

import java.sql.SQLException;
import java.util.List;

public interface PlayStationDAO {
    String generateNewPlaystationId() throws SQLException, ClassNotFoundException;

    boolean deletePlayStation(String id) throws SQLException, ClassNotFoundException;

    List<PlayStationDto> getAllPlayStations() throws SQLException, ClassNotFoundException;

    boolean savePlayStation(PlayStationDto playStationDto) throws SQLException, ClassNotFoundException;

    PlayStationDto searchModel(String id) throws SQLException, ClassNotFoundException;

    boolean updatePlayStation(PlayStationDto dto) throws SQLException, ClassNotFoundException;
}
