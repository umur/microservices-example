package com.cinetrack.modulith.catalog;

import org.springframework.modulith.events.Externalized;

@Externalized
public record MovieAdded(String movieId, String title) {}
