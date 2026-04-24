package com.cinetrack.review.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "movie_id", nullable = false)
    private UUID movieId;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Review() {}

    public Review(UUID userId, UUID movieId, int rating, String body) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.body = body;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getMovieId() { return movieId; }
    public int getRating() { return rating; }
    public String getBody() { return body; }
    public Instant getCreatedAt() { return createdAt; }
}
