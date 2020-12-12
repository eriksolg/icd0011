package main;

import config.Config;
import config.HsqlDataSource;
import config.PostgresDataSource;
import model.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.EmailService;
import service.TransferService;

public class Main {

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(Config.class, PostgresDataSource.class, HsqlDataSource.class);

        try (ctx) {
            TransferService ts = ctx.getBean(TransferService.class);
            TransferService ts2 = ctx.getBean(TransferService.class);
            EmailService emailService = ctx.getBean(EmailService.class);
            emailService.send("Mail subject");
            ts.transfer(1, "12", "12");

            PersonDao personDao = ctx.getBean(PersonDao.class);
//            Person person1 = new Person("Jaanika");
//            Person person2 = new Person("Andrei");
//            personDao.insertPerson(person1);
//            personDao.insertPerson(person2);
            System.out.println(personDao.getAllPersons());
        }

    }
}