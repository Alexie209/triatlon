package com.example.triatlon.domain.validators;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

}
