package jdbc;

import util.ConnectionInfo;
import util.FileUtil;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private DataSource pool;

    public PersonDao(DataSource pool) {
        this.pool = pool;
    }

    public List<Person> queryPersons() {

        List<Person> personList = new ArrayList<Person>();

        try (Connection conn = pool.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("Select * from person");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                personList.add(new Person(id, name, age));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }

    public Person getPersonById(Long id) {
        Person person = null;
        String query = "SELECT id, name, age FROM person WHERE id = ?";
        try (Connection conn = pool.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long personId = rs.getLong("id");
                String personName = rs.getString("name");
                int personAge = rs.getInt("age");
                person = new Person(personId, personName, personAge);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public Person insertPerson(String name, int age) {
        Person person = null;
        String query = "INSERT INTO person (name ,age) VALUES (?, ?)";
        try (Connection conn = pool.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, new String[] { "id" })) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("unexpected error!");
            }

            person = new Person(rs.getLong("id"), name, age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
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

    public void initializeData() {
        executeQuery("data.sql");
    }
}
