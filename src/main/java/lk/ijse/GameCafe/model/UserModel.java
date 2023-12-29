package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserModel {

    public static boolean verifyCredential(String UserName, String Password) {
        try {
            DbConnection instance = DbConnection.getInstance();
            Connection connection = instance.getConnection();

            String sql = "SELECT Password FROM user WHERE UserName = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,UserName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                if (Password.equals(resultSet.getString(1))){
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean saveUser(UserDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user Values(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,dto.getUserName());
        ps.setString(2, dto.getPassword());
        ps.setString(3, dto.getEmail());

        boolean isSaved = ps.executeUpdate()>0;
        return isSaved;
    }

    public UserDto getEmail(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE UserName = ?";
        ResultSet resultSet = null;

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,username);
        try {
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new UserDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String username, String text) throws SQLException {

        String sql = "UPDATE user SET password=? WHERE UserName=?";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1,text);
            pstm.setString(2,username);
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                return true;
            }
        }
        return false;
    }
}