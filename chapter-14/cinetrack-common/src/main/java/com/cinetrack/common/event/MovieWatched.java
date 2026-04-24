package com.cinetrack.common.event;

import java.time.Instant;

public record MovieWatched(
        String userId,
        String movieId,
        Instant watchedAt,
        int durationSeconds
) {}
