package com.project.DigitalWallet.DTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
    public class UserDtoForAdmin {
    private Long userId;
    private String name;
        public UserDtoForAdmin(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }

    }


