package com.cinetrack.review.api;

import com.cinetrack.review.application.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createReview(@RequestHeader("X-User-Id") UUID userId,
                                        @RequestBody CreateReviewRequest request) {
        return service.createReview(userId, request);
    }

    @GetMapping("/movies/{movieId}")
    public List<ReviewResponse> getReviewsForMovie(@PathVariable UUID movieId) {
        return service.getReviewsForMovie(movieId);
    }

    @GetMapping("/{reviewId}")
    public ReviewResponse getReview(@PathVariable UUID reviewId) {
        return service.getReview(reviewId);
    }
}
