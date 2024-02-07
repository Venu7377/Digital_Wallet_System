package com.project.DigitalWallet.ExceptionHandlers;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String message) {
        super(message);
    }

}
