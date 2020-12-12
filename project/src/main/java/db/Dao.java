package db;

import order.Order;
import order.Order.OrderRow;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Dao {
    private JdbcTemplate template;
    public Dao(JdbcTemplate template) {
        this.template = template;
    }

    public Order insertOrder(Order order) {
        var orderData = new BeanPropertySqlParameterSource(order);
        Number orderId = new SimpleJdbcInsert(template).withTableName("hw_order").usingGeneratedKeyColumns("id").executeAndReturnKey(orderData);
        order.setId(orderId.longValue());

        for (OrderRow orderRow : order.getOrderRows()) {
            var orderRowData = new BeanPropertySqlParameterSource(orderRow);
            new SimpleJdbcInsert(template).withTableName("hw_order_row").usingGeneratedKeyColumns("id").execute(orderRowData);
        }

        return order;
    }

    public Order getOrderById(Long id) {
        String orderQuery = "SELECT id, order_number FROM hw_order WHERE id=?";
        return template.queryForObject(orderQuery, new BeanPropertyRowMapper<>(Order.class), id);
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String orderQuery = "SELECT id, order_number FROM hw_order";

        orderList = template.query(orderQuery, new BeanPropertyRowMapper<>(Order.class));

        for(Order order : orderList) {
            order.setOrderRows(getOrderRows(order.getId()));
        }
        return orderList;
    }

    private List<OrderRow> getOrderRows(Long id) {
        String orderRowQuery = "SELECT hw_order_row.item_name, hw_order_row.quantity, hw_order_row.price FROM hw_order LEFT JOIN hw_order_row ON hw_order.id = hw_order_row.order_id WHERE order_id = ?";
        return template.query(orderRowQuery,new BeanPropertyRowMapper<>(OrderRow.class), id);
    }
}
