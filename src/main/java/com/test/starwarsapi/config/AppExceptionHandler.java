package com.test.starwarsapi.config;

import com.test.starwarsapi.exception.FilmNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public final ResponseEntity<String> handleFilmNotFoundException(FilmNotFoundException e) {
        return ResponseEntity.badRequest().body("Film not found");
    }

}
