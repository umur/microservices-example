package com.cinetrack.recommendation.application;

import com.cinetrack.recommendation.domain.TasteProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RecommendationService {

    private final TasteProfileRepository repository;

    public RecommendationService(TasteProfileRepository repository) {
        this.repository = repository;
    }

    public List<UUID> getRecommendations(String userId, int limit) {
        var genreScores = repository.getGenreScores(userId);
        if (genreScores.isEmpty()) {
            return List.of();
        }
        // In a real system: query catalog-service using top genres.
        // Here we return placeholder UUIDs sized by score count.
        List<UUID> recs = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : genreScores.get().entrySet()) {
            if (recs.size() >= limit) break;
            recs.add(UUID.randomUUID());
        }
        return recs;
    }
}
