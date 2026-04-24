package com.cinetrack.watchlist.api;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.watchlist.application.DuplicateWatchlistItemException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_returns404Body() {
        var ex = new ResourceNotFoundException("WatchlistItem", "some-id");

        var response = handler.handleNotFound(ex);

        assertThat(response.code()).contains("NOT_FOUND");
        assertThat(response.message()).isNotBlank();
        assertThat(response.traceId()).isNotNull();
    }

    @Test
    void handleDuplicate_returns409Body() {
        var ex = new DuplicateWatchlistItemException("movie-uuid");

        var response = handler.handleDuplicate(ex);

        assertThat(response.code()).contains("DUPLICATE");
        assertThat(response.message()).isNotBlank();
        assertThat(response.traceId()).isNotNull();
    }
}
