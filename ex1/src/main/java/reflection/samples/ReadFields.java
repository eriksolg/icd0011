package reflection.samples;

import java.lang.reflect.Field;

public class ReadFields {

    private String field1;

    private int field2;

    public static void main(String[] args) {

        Field[] fields = ReadFields.class.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getName());
            System.out.println(field.getType());
        }

    }
}
