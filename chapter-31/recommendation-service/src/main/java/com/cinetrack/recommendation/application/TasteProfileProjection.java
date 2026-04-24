package com.cinetrack.recommendation.application;

import com.cinetrack.recommendation.domain.TasteProfileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TasteProfileProjection {

    private static final Logger log = LoggerFactory.getLogger(TasteProfileProjection.class);

    private final TasteProfileRepository repository;
    private final ObjectMapper objectMapper;

    public TasteProfileProjection(TasteProfileRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "review.posted", groupId = "${spring.kafka.consumer.group-id}")
    public void onReviewPosted(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            String userId = node.path("userId").asText();
            double rating = node.path("rating").asDouble();

            // Simple heuristic: high rating boosts "liked" signal
            if (rating >= 4) {
                repository.incrementGenreScore(userId, "general", rating);
            }
            log.debug("Updated taste profile for user {} with rating {}", userId, rating);
        } catch (Exception e) {
            log.warn("Failed to process review.posted event: {}", e.getMessage());
        }
    }
}
