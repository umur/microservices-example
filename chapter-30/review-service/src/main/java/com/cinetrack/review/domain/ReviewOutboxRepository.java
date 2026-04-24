package com.cinetrack.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ReviewOutboxRepository extends JpaRepository<ReviewOutboxEvent, UUID> {
    List<ReviewOutboxEvent> findByStatus(ReviewOutboxEvent.Status status);
}
