package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.UserDto;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
    boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException;
    boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean verifyCredential(String UserName, String Password);
}
