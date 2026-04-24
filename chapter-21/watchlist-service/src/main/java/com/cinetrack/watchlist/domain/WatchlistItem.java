package com.cinetrack.watchlist.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "watchlist_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "movie_id"}))
public class WatchlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "movie_id", nullable = false)
    private UUID movieId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WatchStatus status;

    @Column(name = "added_at", nullable = false)
    private Instant addedAt;

    protected WatchlistItem() {}

    public WatchlistItem(UUID userId, UUID movieId, WatchStatus status) {
        this.userId = userId;
        this.movieId = movieId;
        this.status = status;
        this.addedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getMovieId() { return movieId; }
    public WatchStatus getStatus() { return status; }
    public Instant getAddedAt() { return addedAt; }

    public void updateStatus(WatchStatus newStatus) {
        this.status = newStatus;
    }
}
