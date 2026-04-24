package com.cinetrack.watchlist.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WatchlistRepository extends JpaRepository<WatchlistItem, UUID> {
    List<WatchlistItem> findByUserId(UUID userId);
    Optional<WatchlistItem> findByUserIdAndMovieId(UUID userId, UUID movieId);
    boolean existsByUserIdAndMovieId(UUID userId, UUID movieId);
}
