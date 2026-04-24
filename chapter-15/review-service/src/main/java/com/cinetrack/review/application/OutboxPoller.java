package com.cinetrack.review.application;

import com.cinetrack.review.domain.ReviewOutboxEvent;
import com.cinetrack.review.domain.ReviewOutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OutboxPoller {

    private static final Logger log = LoggerFactory.getLogger(OutboxPoller.class);

    private final ReviewOutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPoller(ReviewOutboxRepository outboxRepository,
                        KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void pollAndPublish() {
        var pending = outboxRepository.findByStatus(ReviewOutboxEvent.Status.PENDING);
        for (var event : pending) {
            try {
                kafkaTemplate.send(event.getEventType(), event.getPayload()).get();
                event.markSent();
            } catch (Exception e) {
                log.error("Failed to publish outbox event {}: {}", event.getId(), e.getMessage());
                event.markFailed();
            }
            outboxRepository.save(event);
        }
    }
}
