package order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class Installment {
    private double amount;
    private LocalDate date;
}
