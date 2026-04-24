package com.cinetrack.user.application;

import com.cinetrack.common.exception.CineTrackException;

public class DuplicateEmailException extends CineTrackException {
    public DuplicateEmailException(String email) {
        super("DUPLICATE_EMAIL", "Email already registered: " + email);
    }
}
