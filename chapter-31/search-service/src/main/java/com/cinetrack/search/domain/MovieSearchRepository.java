package com.cinetrack.search.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface MovieSearchRepository extends ReactiveCrudRepository<MovieIndex, UUID> {

    @Query("SELECT * FROM movie_search_index WHERE title ILIKE '%' || :query || '%' OR overview ILIKE '%' || :query || '%'")
    Flux<MovieIndex> searchByTitleOrOverview(String query);
}
