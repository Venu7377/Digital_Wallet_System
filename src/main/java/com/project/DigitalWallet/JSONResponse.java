package com.project.DigitalWallet;

import lombok.Getter;

@Getter
public class JSONResponse {
    private String message;

    public JSONResponse(String message) {
        this.message = message;
    }

}
