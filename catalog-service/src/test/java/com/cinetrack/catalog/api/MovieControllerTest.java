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
class MovieControllerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieController controller;

    @Test
    void getMovie_returnsOkWithBody() {
        UUID id = UUID.randomUUID();
        var movie = new Movie(id, 550L, "Fight Club", 1999, "An insomniac...", "/poster.jpg");
        var response = MovieResponse.from(movie);
        when(movieService.findById(id)).thenReturn(response);

        var result = controller.getMovie(id);

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().title()).isEqualTo("Fight Club");
    }

    @Test
    void search_returnsOkWithList() {
        var movie = new Movie(UUID.randomUUID(), 550L, "Fight Club", 1999, "An insomniac...", "/poster.jpg");
        when(movieService.search("fight")).thenReturn(List.of(MovieResponse.from(movie)));

        var result = controller.search("fight");

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).hasSize(1);
    }

    @Test
    void search_whenNoResults_returnsEmptyList() {
        when(movieService.search("unknown")).thenReturn(List.of());

        var result = controller.search("unknown");

        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).isEmpty();
    }
}
