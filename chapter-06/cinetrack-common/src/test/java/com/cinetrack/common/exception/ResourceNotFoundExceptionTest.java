package com.cinetrack.common.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceNotFoundExceptionTest {

    @Test
    void errorCode_isUppercaseResourceTypeWithSuffix() {
        assertThat(new ResourceNotFoundException("review", "1").getErrorCode())
                .isEqualTo("REVIEW_NOT_FOUND");
    }

    @Test
    void resourceNotFoundException_isCineTrackException() {
        assertThat(new ResourceNotFoundException("Movie", "1"))
                .isInstanceOf(CineTrackException.class);
    }
}
