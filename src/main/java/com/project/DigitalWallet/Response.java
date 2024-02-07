package com.project.DigitalWallet;

import lombok.Getter;

@Getter
public class Response {
    private String message;

    public Response(String message) {
        this.message = message;
    }

}
