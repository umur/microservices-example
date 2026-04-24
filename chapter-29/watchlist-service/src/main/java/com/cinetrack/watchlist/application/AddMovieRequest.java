package com.cinetrack.watchlist.application;

import com.cinetrack.watchlist.domain.WatchStatus;
import java.util.UUID;

public record AddMovieRequest(UUID movieId, WatchStatus status) {}
