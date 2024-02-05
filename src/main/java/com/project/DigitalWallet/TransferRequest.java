package com.project.DigitalWallet;

import com.project.DigitalWallet.Model.Users;
import lombok.Getter;
import lombok.Setter;


    @Setter
    @Getter
    public class TransferRequest {
//        private Long userId1;
        private Long userId2;
        private String username;
        private Users users;

    }


