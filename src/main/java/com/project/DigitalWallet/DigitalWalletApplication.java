package com.project.DigitalWallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@PropertySource("file:D:/Venug/wallet.properties")
//@EnableWebMvc
public class DigitalWalletApplication {
	public static void main(String[] args) {
		SpringApplication.run(DigitalWalletApplication.class, args);
	}

}
