package com.cinetrack.watchlist.api;

import com.cinetrack.common.dto.ErrorResponse;
import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.watchlist.application.DuplicateWatchlistItemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), UUID.randomUUID().toString());
    }

    @ExceptionHandler(DuplicateWatchlistItemException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicate(DuplicateWatchlistItemException ex) {
        return ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), UUID.randomUUID().toString());
    }
}
