package com.project.DigitalWallet.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "users")
@Component
//@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long userId;
//    private String password;
    private String name;
//    @Column(unique = true)
//    private String username;
    private Long contactNumber;
    private double balance;


}
