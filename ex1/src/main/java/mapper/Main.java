package mapper;

import common.Person;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {

        PersonMapper mapper = new PersonMapper();

        Person person = mapper.parse("{\"id\":null,\"firstName\":\"Alice\",\"lastName\":\"Smith\",\"age\":20}");
        // { "firstName": "Alice" }

        System.out.println(person);

        String dataAsString = mapper.stringify(person);

        System.out.println(dataAsString);

    }
}
