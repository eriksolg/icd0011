package jdbc2;

import com.opencsv.bean.CsvToBeanBuilder;
import util.FileUtil;

import javax.sql.DataSource;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeDao {

    private final DataSource dataSource;

    public EmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initializeSchema() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String query = FileUtil.readFileFromClasspath("schema.sql");
            stmt.executeUpdate(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void insertSampleData() {
        String query = "INSERT INTO employee (first_name, last_name, city) VALUES (?, ?, ?)";
        String contents = FileUtil.readFileFromClasspath("us-sample-data.csv");
        List<Employee> employeeList = new CsvToBeanBuilder<Employee>(
                new StringReader(contents)).withType(Employee.class)
                .build().parse();


        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            for (Employee employee : employeeList) {
                stmt.setString(1, employee.getFirstName());
                stmt.setString(2, employee.getLastName());
                stmt.setString(3, employee.getCity());
                stmt.addBatch();
            }
            stmt.executeBatch();
            conn.commit();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void insertEmployee(Employee employee) {
        String query = "INSERT INTO employee (first_name, last_name, city) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getCity());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        String query = "DELETE FROM employee WHERE id=?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Object> queryAllEmployees () {
        return queryAllEmployees(2, "asc", 0, 0, "");
    }

    public List<Object> queryAllEmployees (int sortColumnNumber, String orderDirection, int pageLength, int pageStart, String searchString) {
        List<Employee> employeeList = new ArrayList<>();
        List<Object> results = new ArrayList<>();
        int count = 0;

        if (sortColumnNumber <=0 ) {
            return new ArrayList<>(){{
                add(employeeList);
                add(0);
            }};
        }

        if (pageLength < 0) {
            return new ArrayList<>() {{
                add(employeeList);
                add(0);
            }};
        }

        if (pageStart < 0) {
            return new ArrayList<>(){{
                add(employeeList);
                add(0);
            }};
        }

        if (orderDirection == null) {
            orderDirection = "asc";
        }

        String query = String.format("SELECT id, first_name, last_name, city, count(*) over() as count FROM employee WHERE first_name LIKE ? ORDER BY %d %s LIMIT ? OFFSET ?", sortColumnNumber, orderDirection);
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchString + "%");

            stmt.setInt(2, pageLength);
            stmt.setInt(3, pageStart);

            System.out.print(stmt);


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String city = rs.getString("city");
                count = rs.getInt("count");
                employeeList.add(new Employee(id, firstName, lastName, city));
            }
            System.out.println(count);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        results.add(employeeList);
        results.add(count);

        return results;
    }
}
