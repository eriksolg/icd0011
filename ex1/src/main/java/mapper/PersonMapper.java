package mapper;

import common.Person;
import reflection.FillThisField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;

public class PersonMapper {

    public Person parse(String input) {
        input = input.replace('{', ' ').replace('}', ' ').trim();
        HashMap<String, String> dataContainer = new HashMap<String, String>();

        for (String property : input.split(",")) {
            String propertyName = property.split(":")[0].replace('"', ' ').trim();
            String propertyValue = property.split(":")[1].replace('"', ' ').trim();
            dataContainer.put(propertyName, propertyValue);
        }

        Person person = new Person(dataContainer.get("firstName"), dataContainer.get("lastName"));
        try {
            person.setAge(Integer.parseInt(dataContainer.get("age")));
        } catch (NumberFormatException e) {

        }

        return person;
    }

    public String stringify(Object object) throws IllegalAccessException {
        String string = "{";
        HashMap<String, String> dataContainer = new HashMap<String, String>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            string += String.format("\"%s\":\"%s\",", field.getName(), field.get(object));
        }
        if (string.charAt(string.length() -1) == ',') {
            string = string.substring(0, string.length() - 1);
        }
        string += '}';

        return string;

    }
}
