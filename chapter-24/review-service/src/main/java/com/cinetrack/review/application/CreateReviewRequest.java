package com.cinetrack.review.application;

import java.util.UUID;

public record CreateReviewRequest(UUID movieId, int rating, String body) {}
