package com.cinetrack.recommendation.application;

import com.cinetrack.recommendation.domain.TasteProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    TasteProfileRepository repository;

    @InjectMocks
    RecommendationService service;

    @Test
    void getRecommendations_whenNoProfile_returnsEmptyList() {
        when(repository.getGenreScores("user-1")).thenReturn(Optional.empty());
        var result = service.getRecommendations("user-1", 10);
        assertThat(result).isEmpty();
    }

    @Test
    void getRecommendations_whenProfileExists_returnsUpToLimit() {
        Map<Object, Object> scores = Map.of("action", "5.0", "drama", "3.0", "comedy", "2.0");
        when(repository.getGenreScores("user-1")).thenReturn(Optional.of(scores));

        var result = service.getRecommendations("user-1", 2);

        assertThat(result).hasSize(2);
    }

    @Test
    void getRecommendations_whenProfileHasFewerThanLimit_returnsAll() {
        Map<Object, Object> scores = Map.of("action", "5.0");
        when(repository.getGenreScores("user-1")).thenReturn(Optional.of(scores));

        var result = service.getRecommendations("user-1", 10);

        assertThat(result).hasSize(1);
    }
}
