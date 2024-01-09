package lk.ijse.GameCafe.dao.custom;

import lk.ijse.GameCafe.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {
    boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException;

    List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException;

    String generateNextId() throws SQLException, ClassNotFoundException;

    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
}
