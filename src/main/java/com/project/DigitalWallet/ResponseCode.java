package com.project.DigitalWallet;

public enum ResponseCode {
    SUCCESS("01", "Success"),
    INVALID_INPUT_PARAMETERS("02", "Invalid input parameters"),
    USER_NOT_FOUND("03", "User not found"),
    INVALID_AMOUNT("04","Invalid Amount Entered");
    // Add more response codes as needed

    private final String code;
    private final String description;

    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    }

