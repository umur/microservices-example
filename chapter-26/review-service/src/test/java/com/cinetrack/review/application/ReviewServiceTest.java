package com.cinetrack.review.application;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.review.domain.*;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    ReviewOutboxRepository outboxRepository;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper()
            .disable(MapperFeature.REQUIRE_HANDLERS_FOR_JAVA8_TIMES);

    @InjectMocks
    ReviewService service;

    @Test
    void createReview_savesReviewAndOutboxInSameCallSequence() {
        UUID userId = UUID.randomUUID();
        var request = new CreateReviewRequest(UUID.randomUUID(), 4, "Great film");
        var review = new Review(userId, request.movieId(), request.rating(), request.body());
        when(reviewRepository.save(any())).thenReturn(review);
        when(outboxRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = service.createReview(userId, request);

        assertThat(result.rating()).isEqualTo(4);
        verify(reviewRepository).save(any());
        verify(outboxRepository).save(any());
    }

    @Test
    void createReview_outboxEventHasCorrectType() {
        UUID userId = UUID.randomUUID();
        var request = new CreateReviewRequest(UUID.randomUUID(), 5, "Masterpiece");
        var review = new Review(userId, request.movieId(), 5, "Masterpiece");
        when(reviewRepository.save(any())).thenReturn(review);
        when(outboxRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.createReview(userId, request);

        verify(outboxRepository).save(argThat(event ->
                event.getEventType().equals("review.posted") &&
                event.getStatus() == ReviewOutboxEvent.Status.PENDING
        ));
    }

    @Test
    void getReview_whenExists_returnsResponse() {
        UUID reviewId = UUID.randomUUID();
        var review = new Review(UUID.randomUUID(), UUID.randomUUID(), 3, "Decent");
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        var result = service.getReview(reviewId);

        assertThat(result.rating()).isEqualTo(3);
    }

    @Test
    void getReview_whenNotFound_throwsResourceNotFoundException() {
        UUID reviewId = UUID.randomUUID();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getReview(reviewId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getReviewsForMovie_returnsAllForMovie() {
        UUID movieId = UUID.randomUUID();
        var reviews = List.of(
                new Review(UUID.randomUUID(), movieId, 5, "Love it"),
                new Review(UUID.randomUUID(), movieId, 4, "Good")
        );
        when(reviewRepository.findByMovieId(movieId)).thenReturn(reviews);

        var result = service.getReviewsForMovie(movieId);

        assertThat(result).hasSize(2);
    }
}
