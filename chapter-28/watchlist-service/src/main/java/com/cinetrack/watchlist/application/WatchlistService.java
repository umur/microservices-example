package com.cinetrack.watchlist.application;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.watchlist.domain.WatchlistItem;
import com.cinetrack.watchlist.domain.WatchlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class WatchlistService {

    private final WatchlistRepository repository;

    public WatchlistService(WatchlistRepository repository) {
        this.repository = repository;
    }

    public List<WatchlistItemResponse> getWatchlist(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(WatchlistItemResponse::from)
                .toList();
    }

    @Transactional
    public WatchlistItemResponse addMovie(UUID userId, AddMovieRequest request) {
        if (repository.existsByUserIdAndMovieId(userId, request.movieId())) {
            throw new DuplicateWatchlistItemException(request.movieId().toString());
        }
        var item = new WatchlistItem(userId, request.movieId(), request.status());
        return WatchlistItemResponse.from(repository.save(item));
    }

    @Transactional
    public WatchlistItemResponse updateStatus(UUID userId, UUID movieId, UpdateStatusRequest request) {
        var item = repository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchlistItem", movieId.toString()));
        item.updateStatus(request.status());
        return WatchlistItemResponse.from(repository.save(item));
    }

    @Transactional
    public void removeMovie(UUID userId, UUID movieId) {
        var item = repository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchlistItem", movieId.toString()));
        repository.delete(item);
    }
}
