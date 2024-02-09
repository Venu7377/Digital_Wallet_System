package com.project.DigitalWallet.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "users")
@Component
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long userId;
    private String name;
    private Long contactNumber;
    @Min(value = 0, message = "Balance must be positive")
    private double amount;



}
