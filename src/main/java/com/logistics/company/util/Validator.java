package com.logistics.company.util;

import com.logistics.company.models.enums.ShipmentDeliveryType;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Validator {
    public static String isEmailValid(String email, boolean isRequired) {
        if(email == null || email.isBlank()) {
            if (isRequired) {
                return "Email cannot be empty";
            } else {
                return "";
            }
        }
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean valid = Pattern.compile(regexPattern).matcher(email).matches();
        return valid ? "" : "Invalid email";
    }

    public static String isStringValid(String string, int minLength, int maxLength, boolean isRequired) {
        if (string == null || string.isBlank()) {
            if (isRequired) {
                return "Field cannot be empty";
            } else {
                return "";
            }
        }
        if (string.length() < minLength || string.length() > maxLength) {
            return "Field length must be between " + minLength + " and " + maxLength;
        }
        return "";
    }

    public static String isPasswordValid(String password, boolean isRequired) {
        if (password == null || password.isBlank()) {
            if (isRequired) {
                return "Password cannot be empty";
            } else {
                return "";
            }
        }
        if (password.length() < 8 || password.length() > 64) {
            return "Password length must be between 8 and 64";
        }
        return "";
    }

    public static boolean isIdInvalid(Long id, boolean isRequired) {
        if (id == null) {
            return isRequired;
        }
        return id <= 0;
    }
    
    public static String isIdValidMsg(Long id, boolean isRequired) {
        if (id == null) {
            if (isRequired) {
                return "Id cannot be empty";
            } else {
                return "";
            }
        }
        if (id <= 0) {
            return "Invalid id";
        }
        return "";
    }

    public static String isPhoneNumberValid(String phoneNumber, boolean isRequired) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            if (isRequired) {
                return "Phone number cannot be empty";
            } else {
                return "";
            }
        }
        String phoneRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";
        boolean valid = Pattern.compile(phoneRegex).matcher(phoneNumber).matches();
        return valid ? "" : "Invalid phone number";
    }

    public static String isWeightValid(Integer integer, boolean isRequired) {
        if (integer == null) {
            if (isRequired) {
                return "Weight cannot be empty";
            } else {
                return "";
            }
        }
        if (integer <= 0) {
            return "Weight must be positive";
        }
        if (integer >= Integer.MAX_VALUE - 1) {
            return "Weight must be less than " + Integer.MAX_VALUE;
        }
        return "";
    }

    public static String isPriceValid(BigDecimal decimal, boolean isRequired) {
        if (decimal == null) {
            if (isRequired) {
                return "Price cannot be empty";
            } else {
                return "";
            }
        }
        if (decimal.compareTo(BigDecimal.ZERO) <= 0) {
            return "Price must be positive";
        }
        return "";
    }

    public static String isShipmentTypeValid(
        ShipmentDeliveryType deliveryType,
        Long deliveryOfficeId,
        Long courierEmployeeId
    ) {
        if (deliveryType == null) {
            return "Shipment delivery type cannot be empty";
        }
        if (deliveryType.equals(ShipmentDeliveryType.ADDRESS) && courierEmployeeId == null && deliveryOfficeId != null) {
            return "Shipments to address must not have a delivery office";
        }
        if (deliveryType.equals(ShipmentDeliveryType.OFFICE) && courierEmployeeId != null && deliveryOfficeId == null) {
            return "Shipments to office must not have a courier";
        }
        if (deliveryOfficeId != null && courierEmployeeId != null) {
            return "Shipments to office and address cannot have both a delivery office and courier";
        }
        if (deliveryOfficeId == null && courierEmployeeId == null) {
            return "Shipment must have a delivery office or courier";
        }
        return "";
    }
}
