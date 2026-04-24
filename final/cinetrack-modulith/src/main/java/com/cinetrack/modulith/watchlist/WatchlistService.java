package com.cinetrack.modulith.watchlist;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import com.cinetrack.modulith.catalog.MovieAdded;
import java.util.ArrayList;
import java.util.List;

@Service
public class WatchlistService {

    private final List<WatchlistItem> items = new ArrayList<>();

    @ApplicationModuleListener
    void onMovieAdded(MovieAdded event) {
        // Auto-suggest: no-op in this demo
    }

    public void add(String userId, String movieId) {
        items.add(new WatchlistItem(userId, movieId));
    }

    public List<WatchlistItem> findByUser(String userId) {
        return items.stream().filter(i -> i.userId().equals(userId)).toList();
    }
}
