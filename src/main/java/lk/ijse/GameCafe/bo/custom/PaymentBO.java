package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.PaymentDto;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePay(String text, String value, Date date, Time time, double v) throws SQLException, ClassNotFoundException;
    boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException;
    List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException;
    String generatePaymentId() throws SQLException, ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
}
