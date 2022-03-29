package com.example.triatlon.domain.validators;

import com.example.triatlon.domain.ParticipantProba;
import com.example.triatlon.domain.validators.ValidationException;
import com.example.triatlon.domain.validators.Validator;


public class ParticipantProbaValidator implements Validator<ParticipantProba> {
    public ParticipantProbaValidator() {

    }
    @Override
    public void validate(ParticipantProba entity) throws ValidationException {
        String message = "";
        if (entity.getIdJucator() <= 0L || entity.getIdProba() <= 0L) {
            message += "User id's can't be negative!";
        }

        if (message.length() > 0) {
            throw new ValidationException(message);
        }
    }
}
