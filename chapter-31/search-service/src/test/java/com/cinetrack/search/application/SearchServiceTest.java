package com.cinetrack.search.application;

import com.cinetrack.search.domain.MovieIndex;
import com.cinetrack.search.domain.MovieSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    MovieSearchRepository repository;

    @InjectMocks
    SearchService service;

    @Test
    void search_whenQueryIsBlank_returnsEmpty() {
        StepVerifier.create(service.search("  "))
                .verifyComplete();
    }

    @Test
    void search_whenQueryProvided_returnsResults() {
        var movie = new MovieIndex(UUID.randomUUID(), "Inception", "Dream heist", 2010);
        when(repository.searchByTitleOrOverview("inception")).thenReturn(Flux.just(movie));

        StepVerifier.create(service.search("inception"))
                .expectNextMatches(m -> m.title().equals("Inception"))
                .verifyComplete();
    }
}
