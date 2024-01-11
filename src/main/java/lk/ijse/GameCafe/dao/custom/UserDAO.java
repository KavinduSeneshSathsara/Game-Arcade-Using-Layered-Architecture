package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dao.CrudDAO;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<UserDto> {
    UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
    boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException;
    boolean verifyCredential(String UserName, String Password);
}
