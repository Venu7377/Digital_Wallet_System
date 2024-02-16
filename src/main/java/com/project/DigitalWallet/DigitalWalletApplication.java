package com.project.DigitalWallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:D:/Venug/wallet.properties")
public class DigitalWalletApplication {
	public static void main(String[] args) {
		SpringApplication.run(DigitalWalletApplication.class, args);
	}

}
