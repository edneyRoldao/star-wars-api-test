package com.test.starwarsapi.exception;

public class FilmValidationException extends RuntimeException {

    public FilmValidationException(String message) {
        super(message);
    }

}
