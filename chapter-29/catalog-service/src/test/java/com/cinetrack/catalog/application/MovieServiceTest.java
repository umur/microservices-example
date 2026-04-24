package com.cinetrack.catalog.application;

import com.cinetrack.catalog.domain.Movie;
import com.cinetrack.catalog.domain.MovieRepository;
import com.cinetrack.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void findById_whenMovieExists_returnsResponse() {
        var id = UUID.randomUUID();
        var movie = new Movie(id, 123L, "Inception", 2010, "A mind-bending thriller", "/inception.jpg");
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        var response = movieService.findById(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.title()).isEqualTo("Inception");
        assertThat(response.releaseYear()).isEqualTo(2010);
    }

    @Test
    void findById_whenMovieNotFound_throwsResourceNotFoundException() {
        var id = UUID.randomUUID();
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movieService.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void search_returnsMatchingMovies() {
        var movie = new Movie(UUID.randomUUID(), 456L, "The Matrix", 1999, "A sci-fi classic", "/matrix.jpg");
        when(movieRepository.findByTitleContainingIgnoreCase("matrix")).thenReturn(List.of(movie));

        var results = movieService.search("matrix");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).title()).isEqualTo("The Matrix");
    }

    @Test
    void search_whenNoMatches_returnsEmptyList() {
        when(movieRepository.findByTitleContainingIgnoreCase("xyz")).thenReturn(List.of());

        var results = movieService.search("xyz");

        assertThat(results).isEmpty();
    }
}
