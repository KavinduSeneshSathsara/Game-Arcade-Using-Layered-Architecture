package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.UserDAO;
import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public boolean verifyCredential(String UserName, String Password) {
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

    @Override
    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE UserName = ?", username);

        if(rst.next()){
            return new UserDto(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3)
            );
        }
        return null;
    }

    @Override
    public boolean updatePassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET password = ? WHERE username = ?", username, newPassword);
    }


    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT into user VALUES (?,?,?)",
                user.getUserName(),
                user.getPassword(),
                user.getEmail()
        );
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
