package com.cinetrack.common.event;

import java.time.Instant;

public record ReviewPosted(
        String reviewId,
        String userId,
        String movieId,
        int rating,
        Instant postedAt
) {}
