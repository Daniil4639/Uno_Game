package app.controllers;

import app.exceptions.SuchNameAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoomsConnectionAdvice {

    @ExceptionHandler(SuchNameAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleNameExistsException(SuchNameAlreadyExists ex) {
        return ex.getMessage();
    }
}