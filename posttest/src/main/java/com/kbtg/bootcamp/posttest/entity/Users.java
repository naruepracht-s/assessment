package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users {

    @Id
    @Pattern(regexp = "\\d{10}", message = "User ID should be numeric only")
    @Size(min = 10, max = 10, message = "User ID must be exactly 10 digits")
    private String userId;

    private String username;

    private String password;
}
