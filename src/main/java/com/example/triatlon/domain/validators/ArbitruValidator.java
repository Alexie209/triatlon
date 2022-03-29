package com.example.triatlon.domain.validators;

import com.example.triatlon.domain.Arbitru;
import com.example.triatlon.domain.validators.ValidationException;
import com.example.triatlon.domain.validators.Validator;


public class ArbitruValidator implements Validator<Arbitru> {
    public ArbitruValidator() {

    }
    @Override
    public void validate(Arbitru entity) throws ValidationException {
        String message = "";
        if(entity.getFirstName().length() == 0) {
            message += "First name can't be an empty string!";
        }
        if(entity.getLastName().length() == 0) {
            message += "Last name can't be an empty string!";
        }
        if(entity.getUsername().length() == 0) {
            message += "Username can't be an empty string!";
        }
        if(entity.getPassword().length() == 0) {
            message += "Password can't be an empty string!";
        }
        if (message.length() > 0) {
            throw new ValidationException(message);
        }
    }
}
