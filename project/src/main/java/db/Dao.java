package db;

import order.Order;
import util.FileUtil;

import javax.sql.DataSource;
import java.sql.*;

public class Dao {
    private DataSource pool;
    public Dao(DataSource pool) {
        this.pool = pool;
    }

    public Order insertOrder(String orderNumber) {
        Order order = null;
        String query = "INSERT INTO hw_order (order_number) VALUES (?)";
        try (Connection conn = pool.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, new String[] { "id" })) {
            stmt.setString(1, orderNumber);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("unexpected error!");
            }

            order = new Order(rs.getLong("id"), orderNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public Order getOrderById(Long id) {
        Order order = null;
        String query = "SELECT id, order_name FROM hw_order WHERE id = ?";
        try (Connection conn = pool.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long orderId = rs.getLong("id");
                String orderNumber = rs.getString("order_number");
                order = new Order(orderId, orderNumber);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
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
