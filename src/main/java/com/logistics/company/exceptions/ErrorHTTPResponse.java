package com.logistics.company.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
public class ErrorHTTPResponse {
    private String code;
    private String message;
    private HashMap<String, String> errors;
    private String timestamp;

    public ErrorHTTPResponse(String code, String message, HashMap<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ErrorHTTPResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.errors = null;
        this.timestamp = LocalDateTime.now().toString();
    }
}
