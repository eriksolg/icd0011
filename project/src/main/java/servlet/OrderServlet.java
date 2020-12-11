package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.Dao;
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
import java.util.List;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    private Dao dao;

    @Override
    public void init() throws ServletException {
        this.dao = (Dao) getServletContext().getAttribute("dao");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        if (req.getParameterMap().containsKey("id")) {
            Long id = null;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }
            Order order = dao.getOrderById(id);
            if (order == null) {
                writer.append("{}");
            } else {
                writer.append(new ObjectMapper().writeValueAsString(order));
            }
        } else {
            List<Order> orderList = dao.getAllOrders();
            writer.append(new ObjectMapper().writeValueAsString(orderList));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String input = RequestReader.read(req);
        Order order = new ObjectMapper().readValue(input, Order.class);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        if (order.getOrderNumber() == null) {
            return;
        }
        order = dao.insertOrder(order);

        writer.append(new ObjectMapper().writeValueAsString(order));
    }
}
