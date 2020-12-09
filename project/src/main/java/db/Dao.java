package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import order.Order;
import order.Order.OrderRow;
import org.apache.logging.log4j.LogManager;
import util.FileUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class Dao {
    private DataSource pool;
    private Logger logger;
    public Dao(DataSource pool) {
        this.pool = pool;
        logger = Logger.getLogger("logger");

    }

    public Order insertOrder(Order order) {
        String query = "INSERT INTO hw_order (order_number, order_rows) VALUES (?, ?)";
        try (Connection conn = pool.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, new String[] { "id" })) {

            stmt.setString(1, order.getOrderNumber());
            stmt.setString(2, new ObjectMapper().writeValueAsString(order.getOrderRows()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("unexpected error!");
            }

            order.setId(rs.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize orderRows");
        }

        return order;
    }

    public Order getOrderById(Long id) {
        Order order = null;
        String query = "SELECT id, order_number, order_rows FROM hw_order WHERE id = ?";
        try (Connection conn = pool.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long orderId = rs.getLong("id");
                String orderNumber = rs.getString("order_number");
                String orderRows = rs.getString("order_rows");
                List<OrderRow> orderRowList = new ObjectMapper().readValue(orderRows, new TypeReference<List<OrderRow>>(){});
                order = new Order(orderId, orderNumber, orderRowList);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            logger.severe("Could not process order rows from JSON");
        }
        return order;
    }

    private void executeQuery(String file) {
        try (Connection conn = pool.getConnection(); Statement stmt = conn.createStatement()) {
            String query = FileUtil.readFileFromClasspath(file);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeSchema() {
        executeQuery("schema.sql");
    }
}
