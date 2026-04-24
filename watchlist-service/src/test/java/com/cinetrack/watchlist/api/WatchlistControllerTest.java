package com.cinetrack.watchlist.api;

import com.cinetrack.watchlist.application.*;
import com.cinetrack.watchlist.domain.WatchStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistControllerTest {

    @Mock
    WatchlistService service;

    @InjectMocks
    WatchlistController controller;

    @Test
    void getWatchlist_returnsListFromService() {
        UUID userId = UUID.randomUUID();
        var item = new WatchlistItemResponse(UUID.randomUUID(), userId, UUID.randomUUID(), WatchStatus.WATCHING, Instant.now());
        when(service.getWatchlist(userId)).thenReturn(List.of(item));

        var result = controller.getWatchlist(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).userId()).isEqualTo(userId);
    }

    @Test
    void addMovie_returnsCreatedItem() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        var request = new AddMovieRequest(movieId, WatchStatus.PLAN_TO_WATCH);
        var response = new WatchlistItemResponse(UUID.randomUUID(), userId, movieId, WatchStatus.PLAN_TO_WATCH, Instant.now());
        when(service.addMovie(userId, request)).thenReturn(response);

        var result = controller.addMovie(userId, request);

        assertThat(result.movieId()).isEqualTo(movieId);
        assertThat(result.status()).isEqualTo(WatchStatus.PLAN_TO_WATCH);
    }

    @Test
    void updateStatus_returnsUpdatedItem() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        var request = new UpdateStatusRequest(WatchStatus.COMPLETED);
        var response = new WatchlistItemResponse(UUID.randomUUID(), userId, movieId, WatchStatus.COMPLETED, Instant.now());
        when(service.updateStatus(userId, movieId, request)).thenReturn(response);

        var result = controller.updateStatus(userId, movieId, request);

        assertThat(result.status()).isEqualTo(WatchStatus.COMPLETED);
        assertThat(result.movieId()).isEqualTo(movieId);
    }

    @Test
    void removeMovie_callsService() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        doNothing().when(service).removeMovie(userId, movieId);

        controller.removeMovie(userId, movieId);

        verify(service).removeMovie(userId, movieId);
    }
}
