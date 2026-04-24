package com.cinetrack.recommendation.application;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RecommendationBatchService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationBatchService.class);
    private static final String LOCK_KEY = "recommendation:batch:lock";

    private final RedissonClient redissonClient;

    public RecommendationBatchService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Scheduled(fixedDelay = 60000)
    public void rebuildRecommendations() {
        RLock lock = redissonClient.getLock(LOCK_KEY);
        boolean acquired = false;
        try {
            acquired = lock.tryLock(0, 55, TimeUnit.SECONDS);
            if (acquired) {
                log.info("Rebuilding recommendation cache...");
                // Batch rebuild logic goes here
            } else {
                log.debug("Skipping batch — another instance holds the lock");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while acquiring recommendation batch lock");
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
