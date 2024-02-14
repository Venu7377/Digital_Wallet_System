package com.project.DigitalWallet.ExceptionHandlers;
import com.project.DigitalWallet.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class InvalidAmountExceptionHandler {

    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<ApiResponse<String>> exception(InvalidAmountException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid Amount", exception.getMessage()));
    }
}