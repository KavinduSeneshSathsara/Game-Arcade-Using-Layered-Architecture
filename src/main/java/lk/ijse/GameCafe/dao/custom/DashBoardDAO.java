package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.DashboardTableDto;

import java.sql.SQLException;
import java.util.List;

public interface DashBoardDAO {
    List<DashboardTableDto> dashbaordTableData() throws SQLException, ClassNotFoundException;
}
