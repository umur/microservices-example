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
class MovieControllerV2AdditionalTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieControllerV2 controller;

    @Test
    void getMovie_returnsResponseFromService() {
        UUID id = UUID.randomUUID();
        var movie = new Movie(id, 550L, "Inception", 2010, "Dream heist film", "/inception.jpg");
        var response = MovieResponse.from(movie);
        when(movieService.findById(id)).thenReturn(response);

        var result = controller.getMovie(id);

        assertThat(result.title()).isEqualTo("Inception");
    }

    @Test
    void search_whenEmpty_returnsEmptyList() {
        when(movieService.search("nothing")).thenReturn(List.of());

        var result = controller.search("nothing");

        assertThat(result).isEmpty();
    }

    @Test
    void search_whenMatchFound_returnsResults() {
        var movie = new Movie(UUID.randomUUID(), 680L, "Pulp Fiction", 1994, "Crime film", "/pf.jpg");
        when(movieService.search("pulp")).thenReturn(List.of(MovieResponse.from(movie)));

        var result = controller.search("pulp");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Pulp Fiction");
    }
}
