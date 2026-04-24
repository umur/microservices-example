package com.cinetrack.common.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CineTrackExceptionTest {

    @Test
    void errorCode_isReturnedCorrectly() {
        var ex = new ResourceNotFoundException("Movie", "abc-123");
        assertThat(ex.getErrorCode()).isEqualTo("MOVIE_NOT_FOUND");
    }

    @Test
    void message_containsResourceTypeAndId() {
        var ex = new ResourceNotFoundException("Movie", "abc-123");
        assertThat(ex.getMessage()).contains("Movie").contains("abc-123");
    }

    @Test
    void externalServiceException_hasCorrectCode() {
        var ex = new ExternalServiceException("TMDB", "timeout");
        assertThat(ex.getErrorCode()).isEqualTo("EXTERNAL_SERVICE_ERROR");
    }

    @Test
    void externalServiceException_messageContainsServiceAndDetail() {
        var ex = new ExternalServiceException("TMDB", "timeout");
        assertThat(ex.getMessage()).contains("TMDB").contains("timeout");
    }
}
