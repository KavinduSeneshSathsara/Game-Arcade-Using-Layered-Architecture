package lk.ijse.GameCafe.bo.custom;

import lk.ijse.GameCafe.bo.SuperBO;
import lk.ijse.GameCafe.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException;
    List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException;
    String generatePaymentId() throws SQLException, ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
}
