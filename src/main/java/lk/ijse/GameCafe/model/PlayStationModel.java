package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.PlayStationDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayStationModel {
    public double getRate(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT rate FROM play_station WHERE play_station_id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,id);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        return resultSet.getDouble(1);
    }

    public boolean deletePlayStation(String id) throws SQLException {
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
        ps.setDouble(4,playStationDto.getRate());
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

    public boolean updatePlayStation(PlayStationDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE play_station SET play_station_number = ?, status = ?, rate = ? WHERE play_station_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, dto.getPlayStationNumber());
        ps.setString(2, dto.getStatus());
        ps.setString(3, String.valueOf(dto.getRate()));
        ps.setString(4, dto.getPlayStationId());

        return ps.executeUpdate() > 0;
    }

    public PlayStationDto searchModel(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM play_station WHERE play_station_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();

        PlayStationDto dto = null;

        if (resultSet.next()) {
            String playStationId = resultSet.getString(1);
            String playStationNumber = resultSet.getString(2);
            String status = resultSet.getString(3);
            double rate = Double.parseDouble(resultSet.getString(4));

            dto = new PlayStationDto(playStationId, playStationNumber, status, rate);
        }
        return dto;
    }

    public String generateNewPlaystationId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT CONCAT('P', LPAD(IFNULL(MAX(SUBSTRING(play_station_id, 2)), 0) + 1, 4, '0')) FROM play_station";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }

            return null; // Return null if something goes wrong
        }
    }
}
