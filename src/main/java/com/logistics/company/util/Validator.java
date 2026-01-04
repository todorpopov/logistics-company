package com.logistics.company.util;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isEmailValid(String email) {
        if(email == null || email.isBlank()) {
            return false;
        }
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
            .matcher(email)
            .matches();
    }

    public static boolean isStringValid(String string, int minLength, int maxLength) {
        return string != null && !string.isBlank() && string.length() >= minLength && string.length() <= maxLength;
    }

    public static boolean isIdValid(Long id) {
        return id != null && id > 0;
    }
}
