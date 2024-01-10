package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.PlayStationDAO;
import lk.ijse.GameCafe.dto.PlayStationDto;
import lk.ijse.GameCafe.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayStationDAOImpl implements PlayStationDAO {

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CONCAT('P', LPAD(IFNULL(MAX(SUBSTRING(play_station_id, 2)), 0) + 1, 4, '0')) FROM play_station");

        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM play_station WHERE play_station_id = ?", id);
    }

    @Override
    public List<PlayStationDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM play_station");

        List<PlayStationDto> list = new ArrayList<>();

        while(rst.next()){

            PlayStationDto playStationDto = new PlayStationDto(
              rst.getString(1),
              rst.getString(2),
              rst.getString(3),
              rst.getInt(4)
            );
            list.add(playStationDto);
        }
        return list;
    }

    @Override
    public boolean save(PlayStationDto playStationDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO play_station VALUES (?,?,?,?)",
                playStationDto.getPlayStationId(),
                playStationDto.getPlayStationNumber(),
                playStationDto.getStatus(),
                playStationDto.getRate()
        );
    }

    @Override
    public PlayStationDto search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM play_station WHERE play_station_id = ?", id);

        if (rst.next()){
            return new PlayStationDto(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getInt(4)
            );
        }
        return null;
    }

    @Override
    public boolean update(PlayStationDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE play_station SET play_station_number = ?, status = ?, rate = ? WHERE play_station_id = ?",
                dto.getPlayStationNumber(),
                dto.getStatus(),
                dto.getRate(),
                dto.getPlayStationId()
        );
    }

    @Override
    public double getRate(String station) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT rate FROM play_station WHERE play_station_id = ?", station);

        if (rst.next()) {
            return rst.getDouble("rate");
        }
        return 0.0;
    }

}
