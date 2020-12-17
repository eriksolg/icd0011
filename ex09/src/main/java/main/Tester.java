package main;

import config.DbConfig;
import config.HsqlDataSource;
import dao.PersonDao;
import model.Address;
import model.Person;
import model.Phone;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
              new AnnotationConfigApplicationContext(
                      DbConfig.class, HsqlDataSource.class);

        try (ctx) {

            PersonDao dao = ctx.getBean(PersonDao.class);
            Person person1 = new Person("person12");
            person1.setAddress(new Address("laulupeo"));
            List<Phone> phoneList = new ArrayList<>();
            phoneList.add(new Phone("number"));
            phoneList.add(new Phone("number2"));

            person1.setPhones(phoneList);
            Person person2 = new Person("person2");
            person2.setAddress(new Address("laulupeo2"));

            dao.save(person1);
            dao.save(person2);

            List<Person> personList = dao.findAll();
            for (Person person : personList) {
                System.out.println(person.getAddress());
            }

            Person person = dao.findPersonById(1L);
            person.setName("lol");
            dao.updatePerson(person);
        }




    }
}