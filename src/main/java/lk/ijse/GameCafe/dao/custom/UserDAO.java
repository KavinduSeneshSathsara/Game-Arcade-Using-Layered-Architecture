package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.UserDto;

import java.sql.SQLException;

public interface UserDAO {
    UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
}
