package com.cinetrack.catalog.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
public class WatchlistClient {

    private static final Logger log = LoggerFactory.getLogger(WatchlistClient.class);

    private final RestClient restClient;

    public WatchlistClient(
            RestClient.Builder builder,
            @Value("${services.watchlist.base-url:http://watchlist-service:8083}") String baseUrl) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public List<String> getWatchlistMovieIds(UUID userId) {
        // Watchlist service not yet implemented (Chapter 10). Returns empty for now.
        log.debug("Watchlist service not yet available, returning empty for user {}", userId);
        return List.of();
    }
}
