package com.project.DigitalWallet.ExceptionHandlers;

import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.ResponseCode;
import io.swagger.models.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
    public class ValidationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handle(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errorMessages.add(err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ResponseCode.INVALID_INPUT_PARAMETERS.getCode(), ResponseCode.INVALID_INPUT_PARAMETERS.getDescription(), errorMessages));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handle1(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ResponseCode.INVALID_INPUT_PARAMETERS.getCode(),
                        ResponseCode.INVALID_INPUT_PARAMETERS.getDescription(),
                        "Mobile Number Already Exists"));
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse<String>> exception(InvalidAmountException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ResponseCode.INVALID_AMOUNT.getCode(), ResponseCode.INVALID_AMOUNT.getDescription(), exception.getMessage()));
    }
}


