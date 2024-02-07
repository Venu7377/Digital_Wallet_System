package com.project.DigitalWallet.ExceptionHandlers;
import com.project.DigitalWallet.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class InvalidAmountExceptionHandler {

    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<Object> exception(InvalidAmountException exception) {
        return new ResponseEntity<>(new Response(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}