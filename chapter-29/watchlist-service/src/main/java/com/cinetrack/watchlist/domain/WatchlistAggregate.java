package com.cinetrack.watchlist.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WatchlistAggregate {

    private final UUID userId;
    private final List<UUID> movieIds = new ArrayList<>();
    private final List<WatchlistEvent> uncommittedEvents = new ArrayList<>();

    public WatchlistAggregate(UUID userId) {
        this.userId = userId;
    }

    public static WatchlistAggregate rehydrate(UUID userId, List<WatchlistEvent> events) {
        var aggregate = new WatchlistAggregate(userId);
        for (WatchlistEvent event : events) {
            aggregate.apply(event);
        }
        return aggregate;
    }

    public void addMovie(UUID movieId) {
        if (!movieIds.contains(movieId)) {
            var event = new WatchlistEvent(userId, "MOVIE_ADDED", movieId.toString());
            apply(event);
            uncommittedEvents.add(event);
        }
    }

    public void removeMovie(UUID movieId) {
        if (movieIds.contains(movieId)) {
            var event = new WatchlistEvent(userId, "MOVIE_REMOVED", movieId.toString());
            apply(event);
            uncommittedEvents.add(event);
        }
    }

    private void apply(WatchlistEvent event) {
        switch (event.getEventType()) {
            case "MOVIE_ADDED" -> movieIds.add(UUID.fromString(event.getPayload()));
            case "MOVIE_REMOVED" -> movieIds.remove(UUID.fromString(event.getPayload()));
        }
    }

    public UUID getUserId() { return userId; }
    public List<UUID> getMovieIds() { return Collections.unmodifiableList(movieIds); }
    public List<WatchlistEvent> getUncommittedEvents() { return Collections.unmodifiableList(uncommittedEvents); }
}
