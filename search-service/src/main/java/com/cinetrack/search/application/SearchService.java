package com.cinetrack.search.application;

import com.cinetrack.search.domain.MovieIndex;
import com.cinetrack.search.domain.MovieSearchRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SearchService {

    private final MovieSearchRepository repository;

    public SearchService(MovieSearchRepository repository) {
        this.repository = repository;
    }

    public Flux<MovieIndex> search(String query) {
        if (query == null || query.isBlank()) {
            return Flux.empty();
        }
        return repository.searchByTitleOrOverview(query.trim());
    }
}
