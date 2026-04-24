package com.cinetrack.search.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("movie_search_index")
public record MovieIndex(
    @Id UUID id,
    String title,
    String overview,
    Integer releaseYear
) {}
