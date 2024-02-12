package com.project.DigitalWallet.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Transaction_id;
    private Long userId;
    private String transactionType;
    private double amount;
    private String timestamp;


}
