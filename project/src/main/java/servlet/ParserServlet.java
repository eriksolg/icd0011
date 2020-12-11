package servlet;

import util.JsonParser;
import util.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/parser")
public class ParserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputString = RequestReader.read(req);
        String responseString = "{ ";
        HashMap<String, String> dataMap = JsonParser.parseJson(inputString);

        int iter = 0;
        for(Map.Entry<String, String> entry : dataMap.entrySet()) {
            iter++;

            String key = entry.getKey();
            String value = entry.getValue();

            try {
                int intValue = Integer.parseInt(value);
                if (key.length() > 3) {
                    intValue *= 2;
                }
                responseString += String.format("\"%s\": %d", key, intValue);
            } catch (NumberFormatException e) {
                if (key.length() > 3) {
                    value = key;
                }
                responseString += String.format("\"%s\": \"%s\"", key, value);
            }

            if (iter != dataMap.size()) {
                responseString += ", ";
            }
        }
        responseString += " }";

        PrintWriter writer = resp.getWriter();
        writer.append(responseString);
        resp.setContentType("application/json");
    }
}
