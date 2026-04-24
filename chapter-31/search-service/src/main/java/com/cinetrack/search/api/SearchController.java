package com.cinetrack.search.api;

import com.cinetrack.search.application.SearchService;
import com.cinetrack.search.domain.MovieIndex;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/movies")
    public Flux<MovieIndex> searchMovies(@RequestParam String q) {
        return searchService.search(q);
    }
}
