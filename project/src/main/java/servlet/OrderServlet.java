package servlet;

import order.Order;
import util.JsonParser;
import util.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    Long lastOrderId = 0L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String input = RequestReader.read(req);
            HashMap<String, String> dataMap = JsonParser.parseJson(input);
            String orderNumber = dataMap.get("orderNumber");
            if (orderNumber == null) {
                return;
            }
            Order order = new Order(lastOrderId++, dataMap.get("orderNumber"));

            PrintWriter writer = resp.getWriter();
            writer.append(order.toJson());

            resp.setContentType("application/json");
    }
}
