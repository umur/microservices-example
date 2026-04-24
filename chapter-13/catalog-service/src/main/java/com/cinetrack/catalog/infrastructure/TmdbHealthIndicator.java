package com.cinetrack.catalog.infrastructure;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TmdbHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // In chapter 3, TMDB client is not yet wired. Placeholder returns UP.
        return Health.up().withDetail("api", "TMDB integration pending").build();
    }
}
