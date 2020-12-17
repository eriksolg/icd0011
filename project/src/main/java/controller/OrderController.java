package controller;

import db.Dao;
import order.Installment;
import order.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import service.OrderService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {

    private Dao dao;
    private OrderService orderService;

    public OrderController (Dao dao, OrderService orderService) {
        this.dao = dao;
        this.orderService = orderService;
    }

    @GetMapping("orders")
    protected List<Order> getOrders() {
        return dao.getAllOrders();

    }

    @GetMapping("orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return dao.getOrderById(id);
    }

    @GetMapping("orders/{id}/installments")
    protected List<Installment> getInstallments(@PathVariable Long id,
                                                @RequestParam String start,
                                                @RequestParam String end) {

        return orderService.getInstallments(id, LocalDate.parse(start), LocalDate.parse(end));
    }

    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    protected ResponseEntity<Order> doPost(@RequestBody @Valid Order order) {

        order = dao.insertOrder(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
