package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.PlayStationBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.PlayStationDAO;
import lk.ijse.GameCafe.dao.custom.impl.PlayStationDAOImpl;
import lk.ijse.GameCafe.dto.PlayStationDto;
import lk.ijse.GameCafe.entity.PlayStation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayStationBOImpl implements PlayStationBO {
    PlayStationDAO playStationDAO = (PlayStationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PLAYSTATION);

    @Override
    public String generatePlayStationId() throws SQLException, ClassNotFoundException {
        return playStationDAO.generateId();
    }

    @Override
    public boolean deletePlayStation(String id) throws SQLException, ClassNotFoundException {
        return playStationDAO.delete(id);
    }

    @Override
    public List<PlayStationDto> getAllPlayStations() throws SQLException, ClassNotFoundException {
        List<PlayStation> playStations = playStationDAO.getAll();
        List<PlayStationDto> playStationDtos = new ArrayList<>();

        for (PlayStation playStation : playStations){
            playStationDtos.add(new PlayStationDto(
                    playStation.getPlayStationId(),
                    playStation.getPlayStationNumber(),
                    playStation.getStatus(),
                    playStation.getRate()
            ));
        }
        return playStationDtos;
    }

    @Override
    public boolean savePlayStation(PlayStationDto dto) throws SQLException, ClassNotFoundException {
        return playStationDAO.save(new PlayStation(
                dto.getPlayStationId(),
                dto.getPlayStationNumber(),
                dto.getStatus(),
                dto.getRate()
        ));
    }

    @Override
    public PlayStationDto searchPlayStation(String id) throws SQLException, ClassNotFoundException {
        PlayStation playStation = playStationDAO.search(id);
        PlayStationDto playStationDto = new PlayStationDto(
          playStation.getPlayStationId(),
          playStation.getPlayStationNumber(),
          playStation.getStatus(),
          playStation.getRate()
        );
        return playStationDto;
    }

    @Override
    public boolean updatePlayStation(PlayStationDto dto) throws SQLException, ClassNotFoundException {
        return playStationDAO.update(new PlayStation(
                dto.getPlayStationId(),
                dto.getPlayStationNumber(),
                dto.getStatus(),
                dto.getRate()
        ));
    }

    @Override
    public double getRate(String station) throws SQLException, ClassNotFoundException {
        return playStationDAO.getRate(station);
    }
}
