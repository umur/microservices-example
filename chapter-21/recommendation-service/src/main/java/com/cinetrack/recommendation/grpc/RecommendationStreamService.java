package com.cinetrack.recommendation.grpc;

import com.cinetrack.recommendation.application.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * gRPC server-streaming service for recommendations.
 * In production, extend the generated RecommendationServiceGrpc.RecommendationServiceImplBase
 * after running protoc on recommendation.proto.
 */
@Service
public class RecommendationStreamService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationStreamService.class);

    private final RecommendationService recommendationService;

    public RecommendationStreamService(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    public List<UUID> streamRecommendations(String userId, int limit) {
        log.debug("Streaming {} recommendations for user {}", limit, userId);
        return recommendationService.getRecommendations(userId, limit);
    }
}
