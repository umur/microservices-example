package com.cinetrack.user.api;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.user.application.DuplicateEmailException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_returns404WithNotFoundCode() {
        var ex = new ResourceNotFoundException("User", "abc-123");

        var result = handler.handleNotFound(ex);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().code()).isEqualTo("USER_NOT_FOUND");
        assertThat(result.getBody().message()).contains("abc-123");
    }

    @Test
    void handleDuplicateEmail_returns409WithDuplicateEmailCode() {
        var ex = new DuplicateEmailException("alice@example.com");

        var result = handler.handleDuplicateEmail(ex);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().code()).isEqualTo("DUPLICATE_EMAIL");
        assertThat(result.getBody().message()).contains("alice@example.com");
    }
}
