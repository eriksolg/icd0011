package util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class RequestReader {
    public static String read(HttpServletRequest req) {
        String input = "";
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            input = buffer.toString();

        } catch (Exception e) {
            System.out.println(e);
        }
        return input;
    }
}
