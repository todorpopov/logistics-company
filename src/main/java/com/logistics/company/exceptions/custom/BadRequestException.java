package com.logistics.company.exceptions.custom;

import java.util.HashMap;

public class BadRequestException extends RuntimeException {
    private HashMap<String, String> errors;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, HashMap<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setError(String field, String message) {
        this.errors.put(field, message);
    }
}
