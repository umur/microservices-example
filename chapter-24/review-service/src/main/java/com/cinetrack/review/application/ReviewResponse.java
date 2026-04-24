package com.cinetrack.review.application;

import com.cinetrack.review.domain.Review;
import java.time.Instant;
import java.util.UUID;

public record ReviewResponse(UUID id, UUID userId, UUID movieId, int rating, String body, Instant createdAt) {
    public static ReviewResponse from(Review r) {
        return new ReviewResponse(r.getId(), r.getUserId(), r.getMovieId(), r.getRating(), r.getBody(), r.getCreatedAt());
    }
}
