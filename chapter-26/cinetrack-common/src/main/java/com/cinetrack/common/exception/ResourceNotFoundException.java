package com.cinetrack.common.exception;

public class ResourceNotFoundException extends CineTrackException {

    public ResourceNotFoundException(String resourceType, Object id) {
        super(
            resourceType.toUpperCase() + "_NOT_FOUND",
            resourceType + " not found: " + id
        );
    }
}
