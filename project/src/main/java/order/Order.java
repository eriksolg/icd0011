package order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    private List<OrderRow> orderRows;

    public Order() {}

    public Order(Long id, String orderNumber) {
        this.id = id;
        this.orderNumber = orderNumber;
    }

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OrderRow {
        private String itemName;
        private int quantity;
        private Double price;

        public OrderRow(){};
    }
}
