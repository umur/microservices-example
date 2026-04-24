package com.cinetrack.catalog.api;

import com.cinetrack.catalog.application.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/movies")
public class MovieControllerV2 {

    private final MovieService movieService;

    public MovieControllerV2(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public MovieResponse getMovie(@PathVariable UUID id) {
        return movieService.findById(id);
    }

    @GetMapping("/search")
    public List<MovieResponse> search(@RequestParam String q) {
        return movieService.search(q);
    }
}
