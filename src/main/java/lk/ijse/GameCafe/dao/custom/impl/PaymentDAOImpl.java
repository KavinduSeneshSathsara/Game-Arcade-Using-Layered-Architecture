package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.PaymentDAO;
import lk.ijse.GameCafe.dto.PaymentDto;
import lk.ijse.GameCafe.dao.SQLUtil;
import lk.ijse.GameCafe.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into payment value (?,?,?,?,?)",
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getDate(),
                payment.getTime(),
                payment.getAmount()
        );
    }

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment");

        List<Payment> list = new ArrayList<>();

        while (rst.next()){
            Payment payment = new Payment(
                rst.getString(1),
                rst.getString(2),
                rst.getDate(3),
                rst.getTime(4),
                rst.getDouble(5));
            list.add(payment);
        }
        return list;
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment ORDER BY payment_id DESC LIMIT 1");
        String  current = null;

        if (rst.next()) {
            current = rst.getString(1);
            return splitId(current);
        }
        return splitId(null);
    }

    private String splitId(String current) {
        if (current != null) {
            String[] split = current.split("P");
            int id = Integer.parseInt(split[1]);
            id++;
            if (9 >= id && id > 0) return "P00" + id;
            else if (99 >= id && id > 9) return "P0" + id;
            else if (id > 99) return String.valueOf(id);
        }
        return "P001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM payment WHERE payment_id = ?", id);
    }
}
