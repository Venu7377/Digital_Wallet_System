package com.project.DigitalWallet.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

    @Getter
    @Configuration
    public class MoneyConfig {

        @Value("${money.load.max}")
        private double maxLoadAmount;

        @Value("${money.load.min}")
        private double minLoadAmount;

        @Value("${money.transfer.max}")
        private double maxTransferAmount;

    }

