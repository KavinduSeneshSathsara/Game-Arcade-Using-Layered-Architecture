package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.DashBoardDAO;
import lk.ijse.GameCafe.dto.DashboardTableDto;
import lk.ijse.GameCafe.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardDAOImpl implements DashBoardDAO {

    @Override
    public List<DashboardTableDto> dashbaordTableData() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT booking_id, cus_id, total FROM booking;");

        List<DashboardTableDto> list = new ArrayList<>();

        while (rst.next()) {
            DashboardTableDto dto = new DashboardTableDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3));
            list.add(dto);
        }
        return list;
    }
}
