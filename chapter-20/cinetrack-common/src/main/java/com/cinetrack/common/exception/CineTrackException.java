package com.cinetrack.common.exception;

public abstract class CineTrackException extends RuntimeException {

    private final String errorCode;

    protected CineTrackException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
