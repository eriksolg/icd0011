package db;

import order.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class Dao {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public Order insertOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Order getOrderById(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> getAllOrders() {
        return entityManager.createQuery("from Order o").getResultList();
    }
}
