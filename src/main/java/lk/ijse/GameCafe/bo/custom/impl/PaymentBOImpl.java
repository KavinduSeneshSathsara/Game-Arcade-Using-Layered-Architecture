package lk.ijse.GameCafe.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.GameCafe.bo.custom.PaymentBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dao.custom.PaymentDAO;
import lk.ijse.GameCafe.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.GameCafe.dto.PaymentDto;
import lk.ijse.GameCafe.entity.Payment;
import lk.ijse.GameCafe.util.TransactionUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    BookingDAO bookingDAO = (BookingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKING);

    @Override
    public boolean savePay(String text, String value, Date date, Time time, double v) throws SQLException, ClassNotFoundException {
        try{
            TransactionUtil.startTransaction();

            boolean s1 = paymentDAO.save(new Payment(text, value, date, time, v));
            if (!s1){

                TransactionUtil.rollBack();
                return false;
            }

            boolean s2 = bookingDAO.updateStatus(value);

            if(!s2){
                TransactionUtil.rollBack();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            TransactionUtil.rollBack();
        }finally {
            TransactionUtil.endTransaction();
        }
        return true;
    }

        @Override
    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(
                dto.getPaymentId(),
                dto.getBookingId(),
                dto.getDate(),
                dto.getTime(),
                dto.getAmount()
        ));
    }

    @Override
    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment : payments){
            paymentDtos.add(new PaymentDto(
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getDate(),
                    payment.getTime(),
                    payment.getAmount()
            ));
        }
        return paymentDtos;
    }

    @Override
    public String generatePaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateId();
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }
}
