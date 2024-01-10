package lk.ijse.GameCafe.dao;

import lk.ijse.GameCafe.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory() {
        return (daoFactory==null) ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        BOOKING, BOOKINGDETAIL, CUSTOMER, DASHBOARD, EMPLOYEE, PAYMENT, PLAYSTATION, USER, QUERY
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case BOOKING:
                return new BookingDAOImpl();
            case BOOKINGDETAIL:
                return new BookingDetailDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DASHBOARD:
                return new DashBoardDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PLAYSTATION:
                return new PlayStationDAOImpl();
            case USER:
                return new UserDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
