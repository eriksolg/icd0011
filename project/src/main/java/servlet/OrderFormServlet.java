package servlet;

import db.Dao;
import order.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/orders/form")
public class OrderFormServlet extends HttpServlet {

    private Dao dao;

    @Override
    public void init() throws ServletException {
        this.dao = (Dao) getServletContext().getAttribute("dao");
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderNumber = req.getParameter("orderNumber");
        Order order = new Order(orderNumber);
        Long id = dao.insertOrder(order).getId();

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/plain");
        writer.append(id.toString());
    }
}
