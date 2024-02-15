package com.project.DigitalWallet.ExceptionHandlers;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {

    private final Long userId;

    public InvalidPasswordException(Long userId) {
        this.userId = userId;
    }
}


