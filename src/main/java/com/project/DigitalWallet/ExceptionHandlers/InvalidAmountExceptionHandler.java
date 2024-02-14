package com.project.DigitalWallet.ExceptionHandlers;
import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class InvalidAmountExceptionHandler {

    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<ApiResponse<String>> exception(InvalidAmountException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ResponseCode.INVALID_AMOUNT.getCode(), ResponseCode.INVALID_AMOUNT.getDescription(), exception.getMessage()));
    }
}