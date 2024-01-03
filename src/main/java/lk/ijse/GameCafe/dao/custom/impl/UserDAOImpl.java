package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.UserDAO;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

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
}
