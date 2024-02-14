package com.project.DigitalWallet.DTO;

import com.project.DigitalWallet.Model.Users;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WalletCreationResponse {

    private String Password;
    private Long userId;
    private String name;
    private String contactNumber;
    private double amount;

    public WalletCreationResponse(Users user, String generatedPassword) {
        this.name= user.getName();
        this.userId=user.getUserId();
        this.contactNumber=user.getContactNumber();
        this.amount=user.getAmount();
        this.Password = generatedPassword;
    }


}
