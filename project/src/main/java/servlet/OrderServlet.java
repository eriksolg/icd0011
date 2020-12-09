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
import java.util.logging.Logger;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    private Dao dao;
    private Logger logger;

    @Override
    public void init() throws ServletException {
        this.dao = (Dao) getServletContext().getAttribute("dao");
        logger = Logger.getLogger("logger");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.severe("Could not parse id");
            return;
        }
        Order order = dao.getOrderById(id);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        if (order == null) {
            writer.append("{}");
        } else {
            writer.append(new ObjectMapper().writeValueAsString(order));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String input = RequestReader.read(req);
        Order order = new ObjectMapper().readValue(input, Order.class);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        if (order.getOrderNumber() == null) {
            logger.warning("Order number not entered.");
            return;
        }
        order = dao.insertOrder(order);

        writer.append(new ObjectMapper().writeValueAsString(order));
    }
}
