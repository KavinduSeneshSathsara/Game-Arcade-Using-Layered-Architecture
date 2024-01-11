package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.UserBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.UserDAO;
import lk.ijse.GameCafe.dao.custom.impl.UserDAOImpl;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException {
        return userDAO.getEmail(username);
    }

    @Override
    public boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException {
        return userDAO.updatePassword(username, text);
    }

    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(
                dto.getUserName(),
                dto.getEmail(),
                dto.getPassword()
        ));
    }

    @Override
    public boolean verifyCredential(String UserName, String Password) {
        return userDAO.verifyCredential(UserName, Password);
    }
}
