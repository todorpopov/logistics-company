package com.logistics.company.exceptions;

import com.logistics.company.exceptions.custom.BadRequestException;
import com.logistics.company.exceptions.custom.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHTTPExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorHTTPResponse> handleBadRequestException(BadRequestException e) {
        ErrorHTTPResponse error = new ErrorHTTPResponse("BAD_REQUEST", e.getMessage(), e.getErrors());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHTTPResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorHTTPResponse error = new ErrorHTTPResponse(
            "UNIQUE_CONSTRAINT_VIOLATION",
            "Unique constraint violation"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHTTPResponse> handleUnauthorizedException(UnauthorizedException e) {
        ErrorHTTPResponse error = new ErrorHTTPResponse("UNAUTHORIZED", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
