package com.cinetrack.catalog.infrastructure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WatchlistClientTest {

    @Test
    void getWatchlistMovieIds_returnsEmptyList_whenServiceNotYetAvailable() {
        // WatchlistClient stub — returns empty until ch10
        var client = new WatchlistClient(
                org.springframework.web.client.RestClient.builder(),
                "http://localhost:8083"
        );

        var result = client.getWatchlistMovieIds(UUID.randomUUID());

        assertThat(result).isEmpty();
    }
}
