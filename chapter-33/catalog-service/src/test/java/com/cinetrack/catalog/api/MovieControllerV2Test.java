package com.cinetrack.catalog.api;

import com.cinetrack.catalog.application.MovieService;
import com.cinetrack.catalog.domain.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerV2Test {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieControllerV2 controller;

    @Test
    void search_returnsList() {
        var movie = new Movie(UUID.randomUUID(), 27205L, "Inception", 2010, "A thief who steals corporate secrets.", "/poster.jpg");
        when(movieService.search("Inception")).thenReturn(List.of(MovieResponse.from(movie)));

        var result = controller.search("Inception");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Inception");
    }
}
