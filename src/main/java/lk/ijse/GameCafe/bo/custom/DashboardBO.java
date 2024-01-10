package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.dto.DashboardTableDto;

import java.sql.SQLException;
import java.util.List;

public interface DashboardBO {
    List<DashboardTableDto> dashboardTableData() throws SQLException, ClassNotFoundException;
}
