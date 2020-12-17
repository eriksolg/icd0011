package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "isik")
public class Person extends BaseEntity{

    @NonNull
    @Column(name = "nimi")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aadressi_id")
    private Address address;

    @ElementCollection
    @CollectionTable(
            name = "isiku_telefonid",
            joinColumns=@JoinColumn(name = "isiku_id",
                    referencedColumnName = "id")
    )
    private List<Phone> phones = new ArrayList<>();
}
