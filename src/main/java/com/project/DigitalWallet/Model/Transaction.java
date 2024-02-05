package com.project.DigitalWallet.Model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")

public class Transaction {
//    @ManyToOne
//
//    @JoinColumn(name = "userId")
//
//    private Users us;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Transaction_id;

    private Long userId;

    private String transactionType;

    private double amount;

    private String timestamp;


}
