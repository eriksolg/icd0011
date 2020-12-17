package order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "orderId" })
@Embeddable
public class OrderRow {
    @Column(name = "item_name")
    private String itemName;

    @NotNull
    @Range(min=1)
    private int quantity;

    @NotNull
    @Range(min=1)
    private int price;
}