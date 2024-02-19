package com.project.DigitalWallet;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("01", "Success"),
    INVALID_INPUT_PARAMETERS("02", "Invalid input parameters"),
    USER_NOT_FOUND("03", "User not found"),
    INVALID_AMOUNT("04","Transaction Failed..Invalid Amount Entered"),
    TRANSACTION_SUCCESS("05","Transaction Successful"),
    CLOSED_CHANNEL_ERROR("06", "Closed channel error"),
    WEB_CLIENT_ERROR("07","Web Client Response Error"),
    TRANSACTION_LIMIT("08","Transaction Limit Exceeded");

    private final String code;
    private final String description;

    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}

