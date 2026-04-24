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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceRemoveTest {

    @Mock WatchlistRepository repository;
    @InjectMocks WatchlistService service;

    @Test
    void removeMovie_whenExists_deletesItem() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        var item = new WatchlistItem(userId, movieId, WatchStatus.WATCHING);
        when(repository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.of(item));

        service.removeMovie(userId, movieId);

        verify(repository).delete(item);
    }

    @Test
    void removeMovie_whenNotFound_throwsResourceNotFoundException() {
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        when(repository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.removeMovie(userId, movieId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
