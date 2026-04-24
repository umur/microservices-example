package com.cinetrack.recommendation.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationBatchServiceTest {

    @Mock
    RedissonClient redissonClient;

    @Mock
    RLock lock;

    @InjectMocks
    RecommendationBatchService service;

    @Test
    void rebuildRecommendations_whenLockAcquired_runsAndReleasesLock() throws Exception {
        when(redissonClient.getLock(anyString())).thenReturn(lock);
        when(lock.tryLock(anyLong(), anyLong(), any())).thenReturn(true);
        when(lock.isHeldByCurrentThread()).thenReturn(true);

        service.rebuildRecommendations();

        verify(lock).unlock();
    }

    @Test
    void rebuildRecommendations_whenLockNotAcquired_skips() throws Exception {
        when(redissonClient.getLock(anyString())).thenReturn(lock);
        when(lock.tryLock(anyLong(), anyLong(), any())).thenReturn(false);

        service.rebuildRecommendations();

        verify(lock, never()).unlock();
    }
}
