package reflection;

import common.Person;
import reflection.samples.ReadFields;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) {

        Person person = new Person("Alice", "Smith");

        Person person2 = new Person();
        Person person3 = new Person();

        // get fields
        Field[] fields = Person.class.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getName());
            System.out.println(field.getType());
        }

        for (Field field : fields) {
            try {
                if (field.getType().equals(String.class)) {
                    field.setAccessible(true);
                    field.set(person2, "overwrite");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        System.out.println(person2);

        for (Field field : fields) {
            try {
                if (field.getAnnotation(FillThisField.class) != null) {
                    field.setAccessible(true);
                    field.set(person3, "overwrite");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println(person3);
    }

}
