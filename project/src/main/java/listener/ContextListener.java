package listener;

import db.ConnectionPoolFactory;
import db.Dao;
import servlet.OrderServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource pool = new ConnectionPoolFactory().createConnectionPool();
        Dao dao = new Dao(pool);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dao", dao);

        dao.initializeSchema();
    }
}
