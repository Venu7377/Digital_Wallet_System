package com.project.DigitalWallet.DTO;
import com.project.DigitalWallet.Model.Users;
import lombok.Data;
@Data

public class DTO2 {



        private Long userId;
    private String username;
        private String name;

        private double balance;


        public DTO2(Users user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.name = user.getName();
            this.balance = user.getBalance();
        }
    }

