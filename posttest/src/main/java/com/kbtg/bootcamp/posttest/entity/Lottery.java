package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "lottery")
@Data
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Pattern(regexp = "[0-9]+", message = "Ticket should be numeric only")
    @Size(min = 6, max = 6, message = "Ticket should be 6 character")
    private String ticket;

    @NotNull
    @Positive(message = "Price must be positive")
    private Integer price;

    @NotNull
    @Min(value = 0, message = "Amount should not be less than 0")
    private Integer amount;

}
