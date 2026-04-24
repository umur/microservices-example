package com.cinetrack.review.application;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.review.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public ReviewService(ReviewRepository reviewRepository,
                         ReviewOutboxRepository outboxRepository,
                         ObjectMapper objectMapper) {
        this.reviewRepository = reviewRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ReviewResponse createReview(UUID userId, CreateReviewRequest request) {
        var review = new Review(userId, request.movieId(), request.rating(), request.body());
        var saved = reviewRepository.save(review);

        var outboxEvent = new ReviewOutboxEvent("review.posted", toJson(ReviewResponse.from(saved)));
        outboxRepository.save(outboxEvent);

        return ReviewResponse.from(saved);
    }

    public List<ReviewResponse> getReviewsForMovie(UUID movieId) {
        return reviewRepository.findByMovieId(movieId).stream()
                .map(ReviewResponse::from)
                .toList();
    }

    public ReviewResponse getReview(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .map(ReviewResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Review", reviewId.toString()));
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize outbox event payload", e);
        }
    }
}
