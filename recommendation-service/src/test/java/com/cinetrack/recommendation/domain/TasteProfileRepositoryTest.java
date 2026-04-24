package com.cinetrack.recommendation.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TasteProfileRepositoryTest {

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings("unchecked")
    @Mock
    HashOperations<String, Object, Object> hashOps;

    @InjectMocks
    TasteProfileRepository repository;

    @Test
    void incrementGenreScore_callsRedisIncrement() {
        when(redisTemplate.opsForHash()).thenReturn(hashOps);

        repository.incrementGenreScore("user-1", "action", 3.0);

        verify(hashOps).increment(eq("taste:user-1:genres"), eq("action"), eq(3.0));
    }

    @Test
    void incrementActorScore_callsRedisIncrement() {
        when(redisTemplate.opsForHash()).thenReturn(hashOps);

        repository.incrementActorScore("user-1", "Tom Hanks", 2.0);

        verify(hashOps).increment(eq("taste:user-1:actors"), eq("Tom Hanks"), eq(2.0));
    }

    @Test
    void getGenreScores_whenEmpty_returnsEmptyOptional() {
        when(redisTemplate.opsForHash()).thenReturn(hashOps);
        when(hashOps.entries(any())).thenReturn(Map.of());

        Optional<Map<Object, Object>> result = repository.getGenreScores("user-1");

        assertThat(result).isEmpty();
    }

    @Test
    void getGenreScores_whenPresent_returnsOptionalWithData() {
        when(redisTemplate.opsForHash()).thenReturn(hashOps);
        when(hashOps.entries(any())).thenReturn(Map.of("action", "5.0"));

        Optional<Map<Object, Object>> result = repository.getGenreScores("user-1");

        assertThat(result).isPresent();
        assertThat(result.get()).containsKey("action");
    }
}
