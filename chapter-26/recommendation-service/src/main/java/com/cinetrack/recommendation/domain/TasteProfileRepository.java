package com.cinetrack.recommendation.domain;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class TasteProfileRepository {

    private static final String KEY_PREFIX = "taste:";

    private final RedisTemplate<String, String> redisTemplate;

    public TasteProfileRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementGenreScore(String userId, String genre, double delta) {
        String key = KEY_PREFIX + userId + ":genres";
        redisTemplate.opsForHash().increment(key, genre, delta);
    }

    public void incrementActorScore(String userId, String actor, double delta) {
        String key = KEY_PREFIX + userId + ":actors";
        redisTemplate.opsForHash().increment(key, actor, delta);
    }

    public Optional<Map<Object, Object>> getGenreScores(String userId) {
        String key = KEY_PREFIX + userId + ":genres";
        Map<Object, Object> scores = redisTemplate.opsForHash().entries(key);
        return scores.isEmpty() ? Optional.empty() : Optional.of(scores);
    }
}
