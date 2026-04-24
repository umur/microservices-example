package com.cinetrack.catalog.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.health.contributor.Status;

import static org.assertj.core.api.Assertions.assertThat;

class TmdbHealthIndicatorTest {

    @Test
    void health_returnsUp() {
        var indicator = new TmdbHealthIndicator();
        var health = indicator.health();
        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    void health_includesApiDetail() {
        var indicator = new TmdbHealthIndicator();
        var health = indicator.health();
        assertThat(health.getDetails()).containsKey("api");
    }
}
