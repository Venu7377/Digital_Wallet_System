package com.project.DigitalWallet;

import com.project.DigitalWallet.Model.Users;
import lombok.Getter;
import lombok.Setter;


    @Setter
    @Getter
    public class TransferRequest {
        private Long FromUserId;
        private Long ToUserId;
//        private String username;
//        private Users users;
        private double balance;

    }


