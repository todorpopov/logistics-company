package com.logistics.company.util;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isEmailValid(String email, boolean isRequired) {
        if(email == null || email.isBlank()) {
            return !isRequired;
        }
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
            .matcher(email)
            .matches();
    }

    public static boolean isStringValid(String string, int minLength, int maxLength, boolean isRequired) {
        if (string == null || string.isBlank()) {
            return !isRequired;
        }
        return string.length() >= minLength && string.length() <= maxLength;
    }

    public static boolean isPasswordValid(String password, boolean isRequired) {
        if (password == null || password.isBlank()) {
            return !isRequired;
        }
        return password.length() >= 8 && password.length() <= 64;
    }

    public static boolean isIdValid(Long id, boolean isRequired) {
        if (id == null) {
            return !isRequired;
        }
        return id > 0;
    }

    public static boolean isPhoneNumberValid(String phoneNumber, boolean isRequired) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return !isRequired;
        }
        String phoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";
        return Pattern.compile(phoneRegex)
                .matcher(phoneNumber)
                .matches();
    }
}
