package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.PlayStationDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayStationModel {
    public double getRate(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT rate  FROM play_station WHERE play_station_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,id);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        return resultSet.getDouble(1);
    }

    public boolean delePlayStation(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM play_station WHERE  play_station_id= ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1,id);

        return ps.executeUpdate() > 0;
    }

    public List<PlayStationDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT *  FROM play_station";
        PreparedStatement ps = connection.prepareStatement(sql);
        List<PlayStationDto> list=new ArrayList<>();
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            list.add(new PlayStationDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }

    public boolean savePlayStation(PlayStationDto playStationDto) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO play_station VALUES (?,?,?,?)";
        PreparedStatement ps= connection.prepareStatement(sql);
        ps.setString(1,playStationDto.getPlayStationId());
        ps.setString(2, playStationDto.getPlayStationNumber());
        ps.setString(3, playStationDto.getStatus());
        ps.setInt(4,playStationDto.getRate());
        return ps.executeUpdate()>0;
    }

    public List<PlayStationDto> getAllStationNotInThisTime(Time startTime, Time endTime) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT *  FROM play_station";
        PreparedStatement ps = connection.prepareStatement(sql);
        List<PlayStationDto> list=new ArrayList<>();
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            list.add(new PlayStationDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }
}
