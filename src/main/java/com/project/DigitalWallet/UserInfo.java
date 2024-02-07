package com.project.DigitalWallet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
    public class UserInfo {
        private Long userId;
        private String name;

        public UserInfo(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }

    }


