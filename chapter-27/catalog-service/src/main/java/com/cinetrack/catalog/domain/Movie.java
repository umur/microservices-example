package com.cinetrack.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    private UUID id;

    @Column(name = "tmdb_id", unique = true)
    private Long tmdbId;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(name = "poster_path")
    private String posterPath;

    protected Movie() {}

    public Movie(UUID id, Long tmdbId, String title, int releaseYear, String overview, String posterPath) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public UUID getId() { return id; }
    public Long getTmdbId() { return tmdbId; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
    public String getOverview() { return overview; }
    public String getPosterPath() { return posterPath; }
}
