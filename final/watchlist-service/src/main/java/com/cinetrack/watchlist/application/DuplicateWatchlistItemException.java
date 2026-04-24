package com.cinetrack.watchlist.application;

import com.cinetrack.common.exception.CineTrackException;

public class DuplicateWatchlistItemException extends CineTrackException {
    public DuplicateWatchlistItemException(String movieId) {
        super("DUPLICATE_WATCHLIST_ITEM", "Movie " + movieId + " is already in the watchlist");
    }
}
