package lk.ijse.GameCafe.bo;

import lk.ijse.GameCafe.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        BOOKING, CUSTOMER, DASHBOARD, EMPLOYEE, PAYMENT, PLAYSTATION, USER
    }

    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case BOOKING:
                return new BookingBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PLAYSTATION:
                return new PlayStationBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
