package com.cinetrack.watchlist.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface WatchlistEventRepository extends JpaRepository<WatchlistEvent, UUID> {
    List<WatchlistEvent> findByAggregateIdOrderByOccurredAtAsc(UUID aggregateId);
}
