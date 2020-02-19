package com.asys1920.service.advice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;

    public static boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateValidator.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
