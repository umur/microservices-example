package com.cinetrack.recommendation.application;

import com.cinetrack.recommendation.domain.TasteProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TasteProfileProjectionTest {

    @Mock
    TasteProfileRepository repository;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    TasteProfileProjection projection;

    @Test
    void onReviewPosted_whenHighRating_incrementsGenreScore() throws Exception {
        String message = "{\"userId\":\"user-1\",\"rating\":5}";
        projection.onReviewPosted(message);
        verify(repository).incrementGenreScore("user-1", "general", 5.0);
    }

    @Test
    void onReviewPosted_whenLowRating_doesNotIncrementScore() {
        String message = "{\"userId\":\"user-1\",\"rating\":3}";
        projection.onReviewPosted(message);
        verify(repository, never()).incrementGenreScore(any(), any(), anyDouble());
    }

    @Test
    void onReviewPosted_whenInvalidJson_doesNotThrow() {
        projection.onReviewPosted("not-json");
        verifyNoInteractions(repository);
    }
}
