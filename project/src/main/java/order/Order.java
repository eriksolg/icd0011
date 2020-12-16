package order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    @Valid
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
    @JsonIgnoreProperties(value = { "orderId" })
    public static class OrderRow {
        private Long orderId;
        private String itemName;

        @NotNull
        @Range(min=1)
        private int quantity;

        @NotNull
        @Range(min=1)
        private Double price;

        public OrderRow(){};
    }
}
