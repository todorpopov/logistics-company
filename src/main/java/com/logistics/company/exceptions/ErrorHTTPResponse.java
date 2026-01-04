package com.logistics.company.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorHTTPResponse {
    private String code;

    private String message;

    private String timestamp;

    public ErrorHTTPResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}
