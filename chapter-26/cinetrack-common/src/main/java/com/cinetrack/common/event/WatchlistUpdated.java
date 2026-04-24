package com.cinetrack.common.event;

import java.time.Instant;

public record WatchlistUpdated(
        String userId,
        String movieId,
        String action,
        Instant occurredAt
) {}
