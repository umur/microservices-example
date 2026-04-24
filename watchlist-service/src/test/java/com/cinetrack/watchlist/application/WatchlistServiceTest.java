package com.cinetrack.watchlist.application;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.watchlist.domain.WatchlistItem;
import com.cinetrack.watchlist.domain.WatchlistRepository;
import com.cinetrack.watchlist.domain.WatchStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock
    WatchlistRepository repository;

    @InjectMocks
    WatchlistService service;

    @Test
    void addMovie_whenNew_savesAndReturnsItem() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        when(repository.existsByUserIdAndMovieId(userId, movieId)).thenReturn(false);
        var saved = new WatchlistItem(userId, movieId, WatchStatus.PLAN_TO_WATCH);
        when(repository.save(any())).thenReturn(saved);

        var result = service.addMovie(userId, new AddMovieRequest(movieId, WatchStatus.PLAN_TO_WATCH));

        assertThat(result.movieId()).isEqualTo(movieId);
        assertThat(result.status()).isEqualTo(WatchStatus.PLAN_TO_WATCH);
    }

    @Test
    void addMovie_whenDuplicate_throwsDuplicateWatchlistItemException() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        when(repository.existsByUserIdAndMovieId(userId, movieId)).thenReturn(true);

        assertThatThrownBy(() -> service.addMovie(userId, new AddMovieRequest(movieId, WatchStatus.PLAN_TO_WATCH)))
                .isInstanceOf(DuplicateWatchlistItemException.class);
    }

    @Test
    void updateStatus_whenItemExists_updatesStatus() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        var item = new WatchlistItem(userId, movieId, WatchStatus.PLAN_TO_WATCH);
        when(repository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.of(item));
        when(repository.save(item)).thenReturn(item);

        var result = service.updateStatus(userId, movieId, new UpdateStatusRequest(WatchStatus.COMPLETED));

        assertThat(result.status()).isEqualTo(WatchStatus.COMPLETED);
    }

    @Test
    void updateStatus_whenItemNotFound_throwsResourceNotFoundException() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        when(repository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateStatus(userId, movieId, new UpdateStatusRequest(WatchStatus.COMPLETED)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getWatchlist_returnsAllItemsForUser() {
        UUID userId = UUID.randomUUID();
        var items = List.of(
                new WatchlistItem(userId, UUID.randomUUID(), WatchStatus.WATCHING),
                new WatchlistItem(userId, UUID.randomUUID(), WatchStatus.COMPLETED)
        );
        when(repository.findByUserId(userId)).thenReturn(items);

        var result = service.getWatchlist(userId);

        assertThat(result).hasSize(2);
    }
}
