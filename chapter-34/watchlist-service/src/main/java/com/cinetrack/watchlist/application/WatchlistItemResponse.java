package com.cinetrack.watchlist.application;

import com.cinetrack.watchlist.domain.WatchlistItem;
import com.cinetrack.watchlist.domain.WatchStatus;
import java.time.Instant;
import java.util.UUID;

public record WatchlistItemResponse(
        UUID id,
        UUID userId,
        UUID movieId,
        WatchStatus status,
        Instant addedAt
) {
    public static WatchlistItemResponse from(WatchlistItem item) {
        return new WatchlistItemResponse(
                item.getId(), item.getUserId(), item.getMovieId(),
                item.getStatus(), item.getAddedAt()
        );
    }
}
