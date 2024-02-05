package com.project.DigitalWallet.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Component
//@Builder
public class Users {
    @OneToMany(mappedBy = "us", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long userId;
    private String password;
    private String name;
    @Column(unique = true)
    private String username;
    private Long contactNumber;
    private double balance;


}
