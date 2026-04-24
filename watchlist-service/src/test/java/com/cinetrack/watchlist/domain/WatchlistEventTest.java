package com.cinetrack.watchlist.domain;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class WatchlistEventTest {

    @Test
    void constructor_setsAllFields() {
        UUID aggregateId = UUID.randomUUID();
        var event = new WatchlistEvent(aggregateId, "MOVIE_ADDED", "movie-uuid");
        assertThat(event.getAggregateId()).isEqualTo(aggregateId);
        assertThat(event.getEventType()).isEqualTo("MOVIE_ADDED");
        assertThat(event.getPayload()).isEqualTo("movie-uuid");
        assertThat(event.getOccurredAt()).isNotNull();
    }

    @Test
    void constructor_withRemoveEvent_setsType() {
        var event = new WatchlistEvent(UUID.randomUUID(), "MOVIE_REMOVED", "movie-uuid");
        assertThat(event.getEventType()).isEqualTo("MOVIE_REMOVED");
    }

    @Test
    void getId_returnsNullForUnpersistedEntity() {
        var event = new WatchlistEvent(UUID.randomUUID(), "MOVIE_ADDED", "payload");
        assertThat(event.getId()).isNull();
    }
}
