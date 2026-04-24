package com.cinetrack.recommendation.grpc;

import com.cinetrack.recommendation.application.RecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationStreamServiceTest {

    @Mock
    RecommendationService recommendationService;

    @InjectMocks
    RecommendationStreamService streamService;

    @Test
    void streamRecommendations_delegatesToRecommendationService() {
        var movieIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(recommendationService.getRecommendations("user-1", 2)).thenReturn(movieIds);

        var result = streamService.streamRecommendations("user-1", 2);

        assertThat(result).hasSize(2);
    }

    @Test
    void streamRecommendations_whenEmpty_returnsEmptyList() {
        when(recommendationService.getRecommendations("user-1", 5)).thenReturn(List.of());

        var result = streamService.streamRecommendations("user-1", 5);

        assertThat(result).isEmpty();
    }
}
