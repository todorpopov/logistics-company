package com.logistics.company.util;

import com.logistics.company.models.enums.ShipmentDeliveryType;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public static boolean isWeightValid(Integer integer, boolean isRequired) {
        if (integer == null) {
            return !isRequired;
        }
        return integer > 0 && integer <= Integer.MAX_VALUE - 1;
    }

    public static boolean isPriceValid(BigDecimal decimal, boolean isRequired) {
        if (decimal == null) {
            return !isRequired;
        }
        return decimal.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isLocalDateValid(LocalDate date, boolean canBeInThePast, boolean isRequired) {
        if (date == null) {
            return !isRequired;
        }
        if (!canBeInThePast) {
            return !date.isBefore(LocalDate.now());
        }
        return true;
    }

    public static boolean isShipmentTypeValid(ShipmentDeliveryType deliveryType, Long deliveryOfficeId, Long courierEmployeeId) {
        if (deliveryType != null && (deliveryType.equals(ShipmentDeliveryType.ADDRESS) && courierEmployeeId == null)) {
            return false;
        }
        if (deliveryType != null && (deliveryType.equals(ShipmentDeliveryType.OFFICE) && courierEmployeeId != null)) {
            return false;
        }
        if (deliveryOfficeId != null && courierEmployeeId != null) {
            return false;
        }
        return !(deliveryOfficeId == null && courierEmployeeId == null);
    }
}
