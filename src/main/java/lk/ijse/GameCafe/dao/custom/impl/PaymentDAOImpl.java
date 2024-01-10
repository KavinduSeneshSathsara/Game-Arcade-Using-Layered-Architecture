package lk.ijse.GameCafe.dao.custom.impl;

import lk.ijse.GameCafe.dao.custom.PaymentDAO;
import lk.ijse.GameCafe.dto.PaymentDto;
import lk.ijse.GameCafe.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into payment value (?,?,?,?,?)",
                paymentDto.getPaymentId(),
                paymentDto.getBookingId(),
                paymentDto.getDate(),
                paymentDto.getTime(),
                paymentDto.getAmount()
        );
    }

    @Override
    public List<PaymentDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM payment");

        List<PaymentDto> list = new ArrayList<>();

        while (rst.next()){
            PaymentDto paymentDto = new PaymentDto(
                rst.getString(1),
                rst.getString(2),
                rst.getDate(3),
                rst.getTime(4),
                rst.getDouble(5));
            list.add(paymentDto);
        }
        return list;
    }

    @Override
    public boolean update(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public PaymentDto search(String id) throws SQLException, ClassNotFoundException {
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
