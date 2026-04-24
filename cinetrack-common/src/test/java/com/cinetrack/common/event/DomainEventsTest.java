package com.cinetrack.common.event;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DomainEventsTest {

    @Test
    void userRegistered_storesFields() {
        var now = Instant.now();
        var event = new UserRegistered("user-1", "alice@example.com", "Alice", now);
        assertThat(event.userId()).isEqualTo("user-1");
        assertThat(event.email()).isEqualTo("alice@example.com");
        assertThat(event.displayName()).isEqualTo("Alice");
        assertThat(event.occurredAt()).isEqualTo(now);
    }

    @Test
    void movieWatched_storesFields() {
        var now = Instant.now();
        var event = new MovieWatched("user-1", "movie-1", now, 7200);
        assertThat(event.userId()).isEqualTo("user-1");
        assertThat(event.movieId()).isEqualTo("movie-1");
        assertThat(event.watchedAt()).isEqualTo(now);
        assertThat(event.durationSeconds()).isEqualTo(7200);
    }

    @Test
    void reviewPosted_storesFields() {
        var now = Instant.now();
        var event = new ReviewPosted("review-1", "user-1", "movie-1", 5, now);
        assertThat(event.reviewId()).isEqualTo("review-1");
        assertThat(event.userId()).isEqualTo("user-1");
        assertThat(event.movieId()).isEqualTo("movie-1");
        assertThat(event.rating()).isEqualTo(5);
        assertThat(event.postedAt()).isEqualTo(now);
    }

    @Test
    void reviewLiked_storesFields() {
        var now = Instant.now();
        var event = new ReviewLiked("review-1", "user-2", now);
        assertThat(event.reviewId()).isEqualTo("review-1");
        assertThat(event.likedByUserId()).isEqualTo("user-2");
        assertThat(event.likedAt()).isEqualTo(now);
    }

    @Test
    void watchlistUpdated_storesFields() {
        var now = Instant.now();
        var event = new WatchlistUpdated("user-1", "movie-1", "ADDED", now);
        assertThat(event.userId()).isEqualTo("user-1");
        assertThat(event.movieId()).isEqualTo("movie-1");
        assertThat(event.action()).isEqualTo("ADDED");
        assertThat(event.occurredAt()).isEqualTo(now);
    }

    @Test
    void records_supportEquality() {
        var now = Instant.now();
        var e1 = new ReviewPosted("r1", "u1", "m1", 4, now);
        var e2 = new ReviewPosted("r1", "u1", "m1", 4, now);
        assertThat(e1).isEqualTo(e2);
        assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
    }
}
