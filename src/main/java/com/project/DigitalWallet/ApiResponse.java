package com.project.DigitalWallet;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private String responseCode;
    private String responseDescription;
    private T responseData;
    public ApiResponse(String responseCode, String responseDescription, T responseData) {
        this.responseCode = responseCode;
        this.responseDescription = responseDescription;
        this.responseData = responseData;
    }
}
