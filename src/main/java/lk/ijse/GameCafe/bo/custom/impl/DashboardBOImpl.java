package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.DashboardBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.DashBoardDAO;
import lk.ijse.GameCafe.dao.custom.impl.DashBoardDAOImpl;
import lk.ijse.GameCafe.dto.DashboardTableDto;

import java.sql.SQLException;
import java.util.List;

public class DashboardBOImpl implements DashboardBO {

    DashBoardDAO dashBoardDAO = (DashBoardDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DASHBOARD);
    @Override
    public List<DashboardTableDto> dashboardTableData() throws SQLException, ClassNotFoundException {
        return dashBoardDAO.dashbaordTableData();
    }
}
