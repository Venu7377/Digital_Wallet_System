package com.project.DigitalWallet.ExceptionHandlers;
import com.project.DigitalWallet.JSONResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class InvalidPasswordExceptionhandler {
    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<Object> exception(InvalidPasswordException exception) {
        return new ResponseEntity<>(new JSONResponse("Invalid Password"), HttpStatus.BAD_REQUEST);
    }
}
