package com.project.DigitalWallet.ExceptionHandlers;

import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.ResponseCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
    public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handle(MethodArgumentNotValidException ex) {
        logger.error("Invalid Name or MobileNumber Entered");
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errorMessages.add(err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        ResponseCode.INVALID_INPUT_PARAMETERS.getCode(),
                        ResponseCode.INVALID_INPUT_PARAMETERS.getDescription(), errorMessages
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handle1(DataIntegrityViolationException ex) {
        logger.error("Error creating wallet for user as Mobile Number Already Exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        ResponseCode.INVALID_INPUT_PARAMETERS.getCode(),
                        ResponseCode.INVALID_INPUT_PARAMETERS.getDescription(),
                        "Mobile Number Already Exists"
                ));
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidAmountException(InvalidAmountException exception) {
        logger.error("Invalid amount error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ResponseCode.INVALID_AMOUNT.getCode(),
                        ResponseCode.INVALID_AMOUNT.getDescription(),
                        exception.getMessage()
                ));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException exception) {
        logger.error("No user found with userId: {}",exception.getUserId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        ResponseCode.USER_NOT_FOUND.getCode(),
                        ResponseCode.USER_NOT_FOUND.getDescription(),
                        "No User Found with UserID: "+exception.getUserId()
                ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidPasswordException(InvalidPasswordException exception) {
        logger.warn("Invalid password for user {}",exception.getUserId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        ResponseCode.INVALID_INPUT_PARAMETERS.getCode(),
                        ResponseCode.INVALID_INPUT_PARAMETERS.getDescription(),
                        "Enter Valid Password"
                ));
    }



    @ExceptionHandler(ClosedChannelException.class)
    public ResponseEntity<ApiResponse<String>> handleClosedChannelException(ClosedChannelException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        ResponseCode.CLOSED_CHANNEL_ERROR.getCode(),
                        ResponseCode.CLOSED_CHANNEL_ERROR.getDescription(),
                        "An unexpected error occurred. Please try again."
                ));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponse<String>> handleWebClientResponseException(WebClientResponseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        ResponseCode.WEB_CLIENT_ERROR.getCode(),
                        ResponseCode.WEB_CLIENT_ERROR.getDescription(),
                        "WebClient response error occurred. Please try again."
                ));
    }
}


