package com.asys1920.userservice.advice;

import com.asys1920.userservice.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Instant;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (isEmailInvalid(user.getEmailAddress())) {
            String field = "emailAddress";
            errors.rejectValue(field, getInvalidationIndicator(field), getInvalidationMessage(field));
        }
        if (isStringInvalid(user.getFirstName())) {
            String field = "firstName";
            errors.rejectValue(field, getInvalidationIndicator(field), getInvalidationMessage(field));
        }
        if (isStringInvalid(user.getLastName())) {
            String field = "lastName";
            errors.rejectValue(field, getInvalidationIndicator(field), getInvalidationMessage(field));
        }
        if (isStringInvalid(user.getUserName())) {
            String field = "userName";
            errors.rejectValue(field, getInvalidationIndicator(field), getInvalidationMessage(field));
        }

        if (isInstantInvalid(user.getExpirationDateDriversLicense())){
            String field = "expirationDateDriversLicense";
            errors.rejectValue(field, getInvalidationIndicator(field), getInvalidationMessage(field));
        }
    }

    private boolean isInstantInvalid(Instant instant) {
        // regex

        // is in past
        isInstantInPast(instant);

        return false;
    }

    private boolean isInstantInPast(Instant instant) {
        return false;
    }

    private String getInvalidationIndicator(String field) {
        return String.format("%s.isInvalid", field);
    }

    private String getInvalidationMessage(String field) {
        return String.format("The submitted %s is not valid, please verify!", field);
    }

    private boolean isEmailInvalid(String emailAddress) {
        // If mail is empty
        if (isStringInvalid(emailAddress)) {
            return true;
        }
        // If mail is in wrong format

        return !emailAddress.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]" +
                "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    }

    private boolean isStringInvalid(String s) {
        // Check if field is empty
        if (s == null || s.isEmpty()) {
            return true;
        }
        return false;
    }
}
