package com.project.DigitalWallet.ExceptionHandlers;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final Long userId;

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }
}
