package com.cinetrack.modulith.catalog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieCatalogTest {

    @Mock
    ApplicationEventPublisher events;

    @InjectMocks
    MovieCatalog catalog;

    @Test
    void add_storesMovieAndPublishesEvent() {
        var movie = new Movie("tt0468569", "The Dark Knight", 2008);
        var result = catalog.add(movie);

        assertThat(result.id()).isEqualTo("tt0468569");
        assertThat(catalog.findAll()).contains(movie);
        verify(events).publishEvent(any(MovieAdded.class));
    }

    @Test
    void findAll_returnsAllAddedMovies() {
        catalog.add(new Movie("m1", "Movie One", 2020));
        catalog.add(new Movie("m2", "Movie Two", 2021));

        assertThat(catalog.findAll()).hasSize(2);
    }

    @Test
    void movieAddedEvent_containsCorrectFields() {
        var event = new MovieAdded("tt001", "Inception");
        assertThat(event.movieId()).isEqualTo("tt001");
        assertThat(event.title()).isEqualTo("Inception");
    }

    @Test
    void movie_record_fields() {
        var movie = new Movie("m1", "Interstellar", 2014);
        assertThat(movie.id()).isEqualTo("m1");
        assertThat(movie.title()).isEqualTo("Interstellar");
        assertThat(movie.releaseYear()).isEqualTo(2014);
    }
}
