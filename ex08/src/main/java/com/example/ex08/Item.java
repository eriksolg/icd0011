package com.example.ex08;


import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Item {
    @Id
    private Long id;

    @NotNull
    @Size(min=2, max=10)
    private String text;
    private boolean done;
}