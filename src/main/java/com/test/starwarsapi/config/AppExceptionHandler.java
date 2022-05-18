package com.test.starwarsapi.config;

import com.test.starwarsapi.exception.FilmNotFoundException;
import com.test.starwarsapi.exception.FilmValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public final ResponseEntity<String> handleFilmNotFoundException(FilmNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film not found");
    }

    @ExceptionHandler(FilmValidationException.class)
    public final ResponseEntity<String> handleFilmNotFoundException(FilmValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
