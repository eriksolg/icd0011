package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Embeddable
public class Phone {


    private String number;

    public Phone(String number) {
        this.number = number;
    }

}
