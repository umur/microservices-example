package com.cinetrack.review.application;

import com.cinetrack.review.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PostReviewSaga {

    private static final Logger log = LoggerFactory.getLogger(PostReviewSaga.class);

    private final SagaStateRepository sagaStateRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PostReviewSaga(SagaStateRepository sagaStateRepository,
                          KafkaTemplate<String, String> kafkaTemplate) {
        this.sagaStateRepository = sagaStateRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public UUID start(String reviewPayload) {
        UUID sagaId = UUID.randomUUID();
        var state = new SagaState(sagaId, "REVIEW_SAVED", reviewPayload);
        sagaStateRepository.save(state);

        try {
            kafkaTemplate.send("saga.review.posted", sagaId.toString(), reviewPayload);
            state.advance("NOTIFICATION_REQUESTED");
            state.complete();
        } catch (Exception e) {
            log.error("Saga {} failed at NOTIFICATION_REQUESTED: {}", sagaId, e.getMessage());
            state.fail();
        }

        sagaStateRepository.save(state);
        return sagaId;
    }

    @Transactional
    public void compensate(UUID sagaId) {
        sagaStateRepository.findBySagaId(sagaId).ifPresent(state -> {
            log.warn("Compensating saga {}", sagaId);
            state.compensate();
            sagaStateRepository.save(state);
        });
    }
}
