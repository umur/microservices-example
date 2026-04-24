package com.cinetrack.watchlist.api;

import com.cinetrack.watchlist.application.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final WatchlistService service;

    public WatchlistController(WatchlistService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<WatchlistItemResponse> getWatchlist(@PathVariable UUID userId) {
        return service.getWatchlist(userId);
    }

    @PostMapping("/{userId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchlistItemResponse addMovie(@PathVariable UUID userId,
                                          @RequestBody AddMovieRequest request) {
        return service.addMovie(userId, request);
    }

    @PatchMapping("/{userId}/items/{movieId}")
    public WatchlistItemResponse updateStatus(@PathVariable UUID userId,
                                               @PathVariable UUID movieId,
                                               @RequestBody UpdateStatusRequest request) {
        return service.updateStatus(userId, movieId, request);
    }

    @DeleteMapping("/{userId}/items/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMovie(@PathVariable UUID userId, @PathVariable UUID movieId) {
        service.removeMovie(userId, movieId);
    }
}
