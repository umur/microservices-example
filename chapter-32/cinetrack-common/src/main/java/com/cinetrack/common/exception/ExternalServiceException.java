package com.cinetrack.common.exception;

public class ExternalServiceException extends CineTrackException {

    public ExternalServiceException(String service, String detail) {
        super(
            "EXTERNAL_SERVICE_ERROR",
            "Call to " + service + " failed: " + detail
        );
    }
}
