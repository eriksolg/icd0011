package db;

import org.apache.commons.dbcp2.BasicDataSource;
import util.PropertyLoader;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPoolFactory {
    public DataSource createConnectionPool() {
        Properties properties = PropertyLoader.loadApplicationProperties();

        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName("org.postgresql.Driver");
        pool.setUrl(properties.getProperty("dbUrl"));
        pool.setUsername(properties.getProperty("dbUser"));
        pool.setPassword(properties.getProperty("dbPassword"));
        pool.setMaxTotal(2);
        pool.setInitialSize(1);

        try {
            // has the side effect of initializing the connection pool
            pool.getLogWriter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pool;
    }
}
