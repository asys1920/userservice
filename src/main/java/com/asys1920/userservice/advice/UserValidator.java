package com.asys1920.userservice.advice;

import com.asys1920.userservice.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Deprecated
public class UserValidator implements Validator {

    private static Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (isEmailInvalid(user.getEmailAddress())) {
            addErrorToResponse("emailAddress", errors);
        }
        if (isStringInvalid(user.getFirstName())) {
            addErrorToResponse("firstName", errors);
        }
        if (isStringInvalid(user.getLastName())) {
            addErrorToResponse("lastName", errors);
        }
        if (isStringInvalid(user.getUserName())) {
            addErrorToResponse("userName", errors);
        }
        try {
            /*if (isDateInvalid(
                    LocalDate.parse(user.getExpirationDateDriversLicense(), DateTimeFormatter.ISO_LOCAL_DATE))) {
                addErrorToResponse("expirationDateDriversLicense", errors);
            }*/
        } catch (DateTimeException ex) {
            addErrorToResponse("expirationDateDriversLicense", errors);
        }
    }

    private boolean isDateInvalid(LocalDate localDate) {
        if (localDate == null) {
            return true;
        }
        // regex
        //DATE_PATTERN.matcher(date).matches();
        // is in past
        return isDateInPast(localDate);
    }

    private boolean isDateInPast(LocalDate localDate) {
        return localDate.isBefore(LocalDate.now());
    }

    private void addErrorToResponse(String field, Errors errors) {
        errors.rejectValue(field, getInvalidationIndicator(field), getInvalidationMessage(field));
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
