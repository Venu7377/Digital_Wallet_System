package com.project.DigitalWallet.DTO;
import com.project.DigitalWallet.Model.Users;
import lombok.Data;

    @Data
    public class UserDTO {
        private Long userId;
        private String name;
        private String username;
        private Long contactNumber;
        private double balance;
//        private String message;

        public UserDTO(Users user) {
            this.userId = user.getUserId();
            this.name = user.getName();
            this.username = user.getUsername();
            this.contactNumber = user.getContactNumber();
            this.balance = user.getBalance();
        }
    }


