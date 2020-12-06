package order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;

    public String toJson() {
        return String.format("{ \"id\": \"%s\", \"orderNumber\": \"%s\" }", id.toString(), orderNumber);
    }
}
