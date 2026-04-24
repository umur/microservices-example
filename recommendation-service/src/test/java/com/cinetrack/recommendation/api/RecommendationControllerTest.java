package com.cinetrack.recommendation.api;

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
class RecommendationControllerTest {

    @Mock
    RecommendationService service;

    @InjectMocks
    RecommendationController controller;

    @Test
    void getRecommendations_returnsListFromService() {
        var ids = List.of(UUID.randomUUID(), UUID.randomUUID());
        when(service.getRecommendations("user-1", 10)).thenReturn(ids);

        var result = controller.getRecommendations("user-1", 10);

        assertThat(result).hasSize(2);
    }

    @Test
    void getRecommendations_whenNoProfile_returnsEmpty() {
        when(service.getRecommendations("user-x", 10)).thenReturn(List.of());

        var result = controller.getRecommendations("user-x", 10);

        assertThat(result).isEmpty();
    }

    @Test
    void getRecommendations_respectsLimit() {
        var ids = List.of(UUID.randomUUID());
        when(service.getRecommendations("user-1", 1)).thenReturn(ids);

        var result = controller.getRecommendations("user-1", 1);

        assertThat(result).hasSize(1);
    }
}
