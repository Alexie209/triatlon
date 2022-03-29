package com.example.triatlon.domain.validators;

import com.example.triatlon.domain.Jucator;


public class JucatorValidator implements Validator<Jucator> {
    public JucatorValidator() {

    }
    @Override
    public void validate(Jucator entity) throws ValidationException {
        String message = "";
        if(entity.getFirstName().length() == 0) {
            message += "First name can't be an empty string!";
        }
        if(entity.getLastName().length() == 0) {
            message += "Last name can't be an empty string!";
        }
        if(entity.getTotalPuncte() < 0) {
            message += "The number of point must be > 0";
        }
        if (message.length() > 0) {
            throw new ValidationException(message);
        }
    }
}

