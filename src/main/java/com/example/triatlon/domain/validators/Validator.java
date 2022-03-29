package com.example.triatlon.domain.validators;

import com.example.triatlon.domain.validators.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}