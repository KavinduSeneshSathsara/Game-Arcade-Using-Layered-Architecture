package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.DashboardTableDto;

import java.sql.SQLException;
import java.util.List;

public interface DashboardBO extends SuperBO {
    List<DashboardTableDto> dashboardTableData() throws SQLException, ClassNotFoundException;
}
