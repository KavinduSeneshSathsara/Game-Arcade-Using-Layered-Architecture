package lk.ijse.GameCafe.bo.custom.impl;

import lk.ijse.GameCafe.bo.custom.PaymentBO;
import lk.ijse.GameCafe.dao.DAOFactory;
import lk.ijse.GameCafe.dao.custom.PaymentDAO;
import lk.ijse.GameCafe.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.GameCafe.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new PaymentDto(
                dto.getPaymentId(),
                dto.getBookingId(),
                dto.getDate(),
                dto.getTime(),
                dto.getAmount()
        ));
    }

    @Override
    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        return paymentDAO.getAll();
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
