package com.example.triatlon.domain.validators;

import com.example.triatlon.domain.ParticipantProba;
import com.example.triatlon.domain.Proba;
import com.example.triatlon.domain.validators.ValidationException;
import com.example.triatlon.domain.validators.Validator;


public class ProbaValidator implements Validator<Proba> {
    public ProbaValidator() {

    }

    @Override
    public void validate(Proba entity) throws ValidationException {
        String message = "";
        if (message.length() > 0) {
            throw new ValidationException(message);
        }
    }
}
