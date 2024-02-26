package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user_ticket")
@Data
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "\\d{10}", message = "User ID should be numeric only")
    @Size(min = 10, max = 10, message = "User ID must be exactly 10 digits")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "lottery_id", referencedColumnName = "id")
    private Lottery lottery;

    private Date purchaseDate;

    public UserTicket(Integer id, String userId, Lottery lottery, Date purchaseDate) {
        this.id = id;
        this.userId = userId;
        this.lottery = lottery;
        this.purchaseDate = purchaseDate;
    }

    public UserTicket() {

    }
}
