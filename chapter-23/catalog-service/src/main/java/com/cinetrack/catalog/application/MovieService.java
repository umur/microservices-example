package com.cinetrack.catalog.application;

import com.cinetrack.catalog.api.MovieResponse;
import com.cinetrack.catalog.domain.MovieRepository;
import com.cinetrack.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieResponse findById(UUID id) {
        return movieRepository.findById(id)
                .map(MovieResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }

    public List<MovieResponse> search(String query) {
        return movieRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .map(MovieResponse::from)
                .toList();
    }
}
