package com.cinetrack.catalog.api;

import com.cinetrack.common.dto.ErrorResponse;
import com.cinetrack.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        var body = ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), "");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
