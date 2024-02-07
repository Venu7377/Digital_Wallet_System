package com.project.DigitalWallet.ExceptionHandlers;


import com.project.DigitalWallet.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception) {
        return new ResponseEntity<>(new Response("No User Found with UserID: "+exception.getUserId()), HttpStatus.NOT_FOUND);
    }
}
