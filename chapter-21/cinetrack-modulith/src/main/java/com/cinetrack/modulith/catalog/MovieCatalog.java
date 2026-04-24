package com.cinetrack.modulith.catalog;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MovieCatalog {

    private final Map<String, Movie> movies = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher events;

    public MovieCatalog(ApplicationEventPublisher events) {
        this.events = events;
    }

    public Movie add(Movie movie) {
        movies.put(movie.id(), movie);
        events.publishEvent(new MovieAdded(movie.id(), movie.title()));
        return movie;
    }

    public List<Movie> findAll() {
        return List.copyOf(movies.values());
    }
}
