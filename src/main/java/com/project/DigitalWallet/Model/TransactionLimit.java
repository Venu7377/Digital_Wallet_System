package com.project.DigitalWallet.Model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transactionLimits")
public class TransactionLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private double totalAmount;
    private int transactionCount;
    private String timestamp;
}
