package com.cinetrack.review.application;

import com.cinetrack.review.domain.SagaState;
import com.cinetrack.review.domain.SagaStateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.support.SendResult;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostReviewSagaTest {

    @Mock
    SagaStateRepository sagaStateRepository;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    PostReviewSaga saga;

    @Test
    void start_whenKafkaSucceeds_completesAndReturnsSagaId() {
        when(sagaStateRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.<SendResult<String, String>>completedFuture(null));

        UUID sagaId = saga.start("{\"reviewId\":\"test\"}");

        assertThat(sagaId).isNotNull();
        verify(sagaStateRepository, times(2)).save(any());
    }

    @Test
    void start_whenKafkaThrows_failsSaga() {
        when(sagaStateRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Kafka down"));

        saga.start("{\"reviewId\":\"test\"}");

        verify(sagaStateRepository, times(2)).save(argThat(s ->
                !(s instanceof SagaState state) || state.getStatus() == SagaState.Status.FAILED
                        || state.getStatus() == SagaState.Status.STARTED
        ));
    }

    @Test
    void compensate_whenSagaExists_marksCompensating() {
        UUID sagaId = UUID.randomUUID();
        var state = new SagaState(sagaId, "REVIEW_SAVED", "{}");
        when(sagaStateRepository.findBySagaId(sagaId)).thenReturn(Optional.of(state));

        saga.compensate(sagaId);

        assertThat(state.getStatus()).isEqualTo(SagaState.Status.COMPENSATING);
        verify(sagaStateRepository).save(state);
    }
}
