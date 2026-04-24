package com.cinetrack.common.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalServiceExceptionTest {

    @Test
    void constructor_setsErrorCode() {
        var ex = new ExternalServiceException("tmdb-service", "connection timeout");
        assertThat(ex.getErrorCode()).isEqualTo("EXTERNAL_SERVICE_ERROR");
    }

    @Test
    void constructor_includesServiceNameInMessage() {
        var ex = new ExternalServiceException("tmdb-service", "connection timeout");
        assertThat(ex.getMessage()).contains("tmdb-service");
    }

    @Test
    void constructor_includesDetailInMessage() {
        var ex = new ExternalServiceException("tmdb-service", "connection timeout");
        assertThat(ex.getMessage()).contains("connection timeout");
    }
}
