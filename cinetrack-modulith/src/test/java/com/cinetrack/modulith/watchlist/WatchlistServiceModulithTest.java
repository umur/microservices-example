package com.cinetrack.modulith.watchlist;

import com.cinetrack.modulith.catalog.MovieAdded;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceModulithTest {

    @InjectMocks
    WatchlistService service;

    @Test
    void add_storesItem() {
        service.add("user-1", "movie-1");

        var items = service.findByUser("user-1");
        assertThat(items).hasSize(1);
        assertThat(items.get(0).userId()).isEqualTo("user-1");
        assertThat(items.get(0).movieId()).isEqualTo("movie-1");
    }

    @Test
    void findByUser_onlyReturnsThatUsersItems() {
        service.add("user-1", "movie-1");
        service.add("user-2", "movie-2");

        assertThat(service.findByUser("user-1")).hasSize(1);
        assertThat(service.findByUser("user-2")).hasSize(1);
    }

    @Test
    void onMovieAdded_doesNotThrow() {
        var event = new MovieAdded("m1", "Inception");
        assertThatCode(() -> service.onMovieAdded(event)).doesNotThrowAnyException();
    }

    @Test
    void watchlistItem_record_fields() {
        var item = new WatchlistItem("user-1", "movie-1");
        assertThat(item.userId()).isEqualTo("user-1");
        assertThat(item.movieId()).isEqualTo("movie-1");
    }
}
