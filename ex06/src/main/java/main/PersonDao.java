package main;

import model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonDao {

    private JdbcTemplate template;

    public PersonDao(JdbcTemplate template) {
        this.template = template;
    }

    public List<Person> getAllPersons() {
        String sql = "select id, name from person";
        return template.query(sql, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person insertPerson(Person person) {
        var data = new BeanPropertySqlParameterSource(person);
        Number id = new SimpleJdbcInsert(template).withTableName("person").usingGeneratedKeyColumns("id").executeAndReturnKey(data);
        person.setId(id.longValue());
        return person;
    }

    private static class PersonMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

            Person person = new Person();
            person.setId(rs.getLong("id"));
            person.setName(rs.getString("name"));

            return person;
        }
    }
}
