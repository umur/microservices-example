package com.cinetrack.catalog.api;

import com.cinetrack.catalog.domain.Movie;

import java.util.UUID;

public record MovieResponse(
        UUID id,
        Long tmdbId,
        String title,
        int releaseYear,
        String overview,
        String posterPath
) {
    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTmdbId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getOverview(),
                movie.getPosterPath()
        );
    }
}
