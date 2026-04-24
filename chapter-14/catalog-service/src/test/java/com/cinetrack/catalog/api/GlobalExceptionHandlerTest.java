package com.cinetrack.catalog.api;

import com.cinetrack.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound_returns404() {
        var ex = new ResourceNotFoundException("Movie", "abc-123");
        var response = handler.handleResourceNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void handleResourceNotFound_responseBodyHasErrorCode() {
        var ex = new ResourceNotFoundException("Movie", "abc-123");
        var response = handler.handleResourceNotFound(ex);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("MOVIE_NOT_FOUND");
    }
}
