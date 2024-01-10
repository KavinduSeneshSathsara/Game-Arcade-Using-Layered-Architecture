package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.dto.PlayStationDto;

import java.sql.SQLException;
import java.util.List;

public interface PlayStationBO {
    String generatePlayStationId() throws SQLException, ClassNotFoundException;
    boolean deletePlayStation(String id) throws SQLException, ClassNotFoundException;
    List<PlayStationDto> getAllPlayStations() throws SQLException, ClassNotFoundException;
    boolean savePlayStation(PlayStationDto playStationDto) throws SQLException, ClassNotFoundException;
    PlayStationDto searchPlayStation(String id) throws SQLException, ClassNotFoundException;
    boolean updatePlayStation(PlayStationDto dto) throws SQLException, ClassNotFoundException;
    double getRate(String station) throws SQLException, ClassNotFoundException;
}
