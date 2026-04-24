package com.cinetrack.watchlist.domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class WatchlistAggregateTest {

    @Test
    void addMovie_appendsToMovieIds() {
        var aggregate = new WatchlistAggregate(UUID.randomUUID());
        var movieId = UUID.randomUUID();
        aggregate.addMovie(movieId);
        assertThat(aggregate.getMovieIds()).contains(movieId);
        assertThat(aggregate.getUncommittedEvents()).hasSize(1);
    }

    @Test
    void addMovie_whenDuplicate_doesNotAdd() {
        var aggregate = new WatchlistAggregate(UUID.randomUUID());
        var movieId = UUID.randomUUID();
        aggregate.addMovie(movieId);
        aggregate.addMovie(movieId);
        assertThat(aggregate.getMovieIds()).hasSize(1);
        assertThat(aggregate.getUncommittedEvents()).hasSize(1);
    }

    @Test
    void removeMovie_removesFromList() {
        var aggregate = new WatchlistAggregate(UUID.randomUUID());
        var movieId = UUID.randomUUID();
        aggregate.addMovie(movieId);
        aggregate.removeMovie(movieId);
        assertThat(aggregate.getMovieIds()).isEmpty();
        assertThat(aggregate.getUncommittedEvents()).hasSize(2);
    }

    @Test
    void rehydrate_rebuildsStateFromEvents() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        var events = List.of(
                new WatchlistEvent(userId, "MOVIE_ADDED", movieId.toString()),
                new WatchlistEvent(userId, "MOVIE_ADDED", UUID.randomUUID().toString()),
                new WatchlistEvent(userId, "MOVIE_REMOVED", movieId.toString())
        );

        var aggregate = WatchlistAggregate.rehydrate(userId, events);

        assertThat(aggregate.getMovieIds()).hasSize(1);
        assertThat(aggregate.getMovieIds()).doesNotContain(movieId);
    }
}
