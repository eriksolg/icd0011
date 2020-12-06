package reflection.samples;

import java.lang.reflect.Field;

public class WriteToField {

    private String greeting = "";

    public static void main(String[] args) throws Exception {

        var instance = new WriteToField();

        Field field = instance.getClass().getDeclaredField("greeting");

        field.setAccessible(true);

        field.set(instance, "Hello!");

        System.out.println(instance.greeting);

    }
}
