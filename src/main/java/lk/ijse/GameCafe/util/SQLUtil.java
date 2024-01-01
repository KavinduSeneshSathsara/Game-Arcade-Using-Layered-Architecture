package lk.ijse.GameCafe.util;

import lk.ijse.GameCafe.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {
    public static <T> T execute(String sql, Object... arg) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < arg.length; i++) {
            statement.setObject((i + 1), arg[i]);
        }

        if (sql.trim().toUpperCase().startsWith("SELECT")){
            return (T) statement.executeQuery();
        }else {
            return (T)(Boolean)(statement.executeUpdate() > 0);
        }
    }
}
