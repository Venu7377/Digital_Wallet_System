package com.project.DigitalWallet;

import lombok.Getter;
import lombok.Setter;

    @Setter
    @Getter
    public class TransferRequest {
        private Long FromUserId;
        private Long ToUserId;
        private double balance;

    }


