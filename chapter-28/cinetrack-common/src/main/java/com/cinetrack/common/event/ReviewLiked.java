package com.cinetrack.common.event;

import java.time.Instant;

public record ReviewLiked(
        String reviewId,
        String likedByUserId,
        Instant likedAt
) {}
