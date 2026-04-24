package com.cinetrack.catalog.infrastructure;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class TmdbClient {

    private static final Logger log = LoggerFactory.getLogger(TmdbClient.class);

    private final RestClient restClient;
    private final String apiKey;

    public TmdbClient(RestClient.Builder restClientBuilder,
                      @Value("${tmdb.api-key:test-key}") String apiKey) {
        this.restClient = restClientBuilder.baseUrl("https://api.themoviedb.org/3").build();
        this.apiKey = apiKey;
    }

    @CircuitBreaker(name = "tmdb", fallbackMethod = "searchFallback")
    @Retry(name = "tmdb")
    public List<Map<String, Object>> searchMovies(String query) {
        return restClient.get()
                .uri("/search/movie?api_key={key}&query={q}", apiKey, query)
                .retrieve()
                .body(List.class);
    }

    @SuppressWarnings("unused")
    List<Map<String, Object>> searchFallback(String query, Throwable t) {
        log.warn("TMDB circuit open for query '{}': {}", query, t.getMessage());
        return Collections.emptyList();
    }
}
