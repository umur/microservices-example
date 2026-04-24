package com.cinetrack.common.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void of_populatesAllFields() {
        var response = ErrorResponse.of("NOT_FOUND", "Resource missing", "trace-123");

        assertThat(response.code()).isEqualTo("NOT_FOUND");
        assertThat(response.message()).isEqualTo("Resource missing");
        assertThat(response.traceId()).isEqualTo("trace-123");
        assertThat(response.timestamp()).isNotNull();
    }

    @Test
    void record_equalityBasedOnFields() {
        var r1 = ErrorResponse.of("CODE", "msg", "t1");
        var r2 = new ErrorResponse("CODE", "msg", "t1", r1.timestamp());

        assertThat(r1).isEqualTo(r2);
    }

    @Test
    void record_toStringContainsCode() {
        var response = ErrorResponse.of("ERR_CODE", "some error", "tid");
        assertThat(response.toString()).contains("ERR_CODE");
    }
}
