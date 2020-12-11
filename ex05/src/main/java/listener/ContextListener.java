package listener;

import jdbc2.EmployeeDao;
import util.DbUtil;
import util.DevDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource dataSource = new DevDataSource(DbUtil.readConnectionInfo());
        EmployeeDao employeeDao = new EmployeeDao(dataSource);
        sce.getServletContext().setAttribute("employeeDao", employeeDao);
        employeeDao.initializeSchema();
        //employeeDao.insertSampleData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
