package com.cinetrack.recommendation.api;

import com.cinetrack.recommendation.application.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<UUID> getRecommendations(@PathVariable String userId,
                                          @RequestParam(defaultValue = "10") int limit) {
        return service.getRecommendations(userId, limit);
    }
}
