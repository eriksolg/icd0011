package jdbc;

import util.ConnectionInfo;
import util.ConnectionPoolFactory;
import util.DbUtil;
import util.FileUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        DataSource pool = new ConnectionPoolFactory().createConnectionPool();

        PersonDao personDao = new PersonDao(pool);

        personDao.initializeSchema();
        personDao.initializeData();

        List<Person> personList = personDao.queryPersons();
        System.out.println(personList);

        Person person = personDao.getPersonById(2L);
        System.out.println(person);

        Person personInserted = personDao.insertPerson("Natasha", 22);
        System.out.println(personInserted);
    }



}
