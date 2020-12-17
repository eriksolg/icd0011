package service;

import db.Dao;
import order.Installment;
import order.Order;
import order.OrderRow;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private Dao dao;

    public OrderService(Dao dao) {
        this.dao = dao;
    }

    public List<Installment> getInstallments(Long id, LocalDate start, LocalDate end) {
        List<Installment> installments = new ArrayList<>();
        int sum = getOrderSum(id);

        Long payments = ChronoUnit.MONTHS.between(YearMonth.from(start), YearMonth.from(end)) + 1;
        System.out.println(payments);
        double amount = Math.floor(sum/payments);
        for (int i = 0; i<payments; i++) {
            Installment installment = new Installment();
            if (i == payments - 1) {
                installment.setAmount((double) sum);
            } else {
                installment.setAmount(amount);
            }
            sum -= amount;

            if (i == 0) {
                installment.setDate(start);
            } else {
                installment.setDate(start.withMonth((start.getMonthValue() + i) > 12 ? (start.getMonthValue() + i) % 12 : start.getMonthValue() + i).withDayOfMonth(1));
            }
            installments.add(installment);
        }

        return installments;
    }

    public int getOrderSum(Long id) {
        Order order = dao.getOrderById(id);
        List<OrderRow> orderRows = order.getOrderRows();
        int sum = 0;
        for (OrderRow orderRow : orderRows) {
            sum += orderRow.getPrice();
        }
        System.out.println(sum);
        return sum;
    }
}
