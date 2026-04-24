package com.cinetrack.search.api;

import com.cinetrack.search.application.SearchService;
import com.cinetrack.search.domain.MovieIndex;
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
class SearchControllerTest {

    @Mock
    SearchService searchService;

    @InjectMocks
    SearchController controller;

    @Test
    void searchMovies_returnsFluxFromService() {
        var movie = new MovieIndex(UUID.randomUUID(), "Inception", "Dream heist", 2010);
        when(searchService.search("inception")).thenReturn(Flux.just(movie));

        StepVerifier.create(controller.searchMovies("inception"))
                .expectNextMatches(m -> m.title().equals("Inception"))
                .verifyComplete();
    }

    @Test
    void searchMovies_whenEmpty_returnsEmptyFlux() {
        when(searchService.search("xyz")).thenReturn(Flux.empty());

        StepVerifier.create(controller.searchMovies("xyz"))
                .verifyComplete();
    }

    @Test
    void searchMovies_returnsAllMatchingResults() {
        var movie1 = new MovieIndex(UUID.randomUUID(), "The Matrix", "Simulation reality", 1999);
        var movie2 = new MovieIndex(UUID.randomUUID(), "Matrix Reloaded", "Sequel", 2003);
        when(searchService.search("matrix")).thenReturn(Flux.just(movie1, movie2));

        StepVerifier.create(controller.searchMovies("matrix"))
                .expectNextMatches(m -> m.title().equals("The Matrix"))
                .expectNextMatches(m -> m.title().equals("Matrix Reloaded"))
                .verifyComplete();
    }
}
