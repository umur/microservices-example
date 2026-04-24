package com.cinetrack.review.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "saga_state")
public class SagaState {

    public enum Status { STARTED, COMPLETED, COMPENSATING, COMPENSATED, FAILED }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "saga_id", nullable = false, unique = true)
    private UUID sagaId;

    @Column(name = "current_step", nullable = false)
    private String currentStep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected SagaState() {}

    public SagaState(UUID sagaId, String initialStep, String payload) {
        this.sagaId = sagaId;
        this.currentStep = initialStep;
        this.status = Status.STARTED;
        this.payload = payload;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getSagaId() { return sagaId; }
    public String getCurrentStep() { return currentStep; }
    public Status getStatus() { return status; }
    public String getPayload() { return payload; }

    public void advance(String nextStep) {
        this.currentStep = nextStep;
        this.updatedAt = Instant.now();
    }

    public void complete() {
        this.status = Status.COMPLETED;
        this.updatedAt = Instant.now();
    }

    public void compensate() {
        this.status = Status.COMPENSATING;
        this.updatedAt = Instant.now();
    }

    public void fail() {
        this.status = Status.FAILED;
        this.updatedAt = Instant.now();
    }
}
