package lk.ijse.GameCafe.model;

import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.DashboardTableDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardModel {

    public List<DashboardTableDto> dashbaordTableData() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT booking_id, cus_id, total FROM booking;";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<DashboardTableDto> list = new ArrayList<>();
        while (resultSet.next()){
            DashboardTableDto dto = new DashboardTableDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3));

            list.add(dto);

        }
        return list;

    }
}
