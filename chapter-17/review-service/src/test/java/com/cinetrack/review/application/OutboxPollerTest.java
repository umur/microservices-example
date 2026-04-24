package com.cinetrack.review.application;

import com.cinetrack.review.domain.ReviewOutboxEvent;
import com.cinetrack.review.domain.ReviewOutboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutboxPollerTest {

    @Mock
    ReviewOutboxRepository outboxRepository;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    OutboxPoller poller;

    @Test
    void pollAndPublish_whenPendingEvents_publishesAndMarksSent() {
        var event = new ReviewOutboxEvent("review.posted", "{\"id\":\"test\"}");
        when(outboxRepository.findByStatus(ReviewOutboxEvent.Status.PENDING)).thenReturn(List.of(event));

        @SuppressWarnings("unchecked")
        var sendResult = mock(org.springframework.kafka.support.SendResult.class);
        when(kafkaTemplate.send(anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(sendResult));

        poller.pollAndPublish();

        assertThat(event.getStatus()).isEqualTo(ReviewOutboxEvent.Status.SENT);
        verify(outboxRepository).save(event);
    }

    @Test
    void pollAndPublish_whenKafkaFails_marksEventFailed() {
        var event = new ReviewOutboxEvent("review.posted", "{\"id\":\"test\"}");
        when(outboxRepository.findByStatus(ReviewOutboxEvent.Status.PENDING)).thenReturn(List.of(event));

        var failedFuture = new CompletableFuture<org.springframework.kafka.support.SendResult<String, String>>();
        failedFuture.completeExceptionally(new RuntimeException("Kafka down"));
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(failedFuture);

        poller.pollAndPublish();

        assertThat(event.getStatus()).isEqualTo(ReviewOutboxEvent.Status.FAILED);
    }
}
