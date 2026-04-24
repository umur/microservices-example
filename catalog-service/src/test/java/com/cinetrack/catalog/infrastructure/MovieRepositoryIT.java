package com.cinetrack.catalog.infrastructure;

import com.cinetrack.catalog.domain.Movie;
import com.cinetrack.catalog.domain.MovieRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
class MovieRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl() + "?sslmode=disable");
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void save_andFindById_returnsMovie() {
        var movie = new Movie(UUID.randomUUID(), 100L, "Interstellar", 2014, "Space epic", "/interstellar.jpg");
        movieRepository.save(movie);

        var found = movieRepository.findById(movie.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Interstellar");
    }

    @Test
    void findByTitleContainingIgnoreCase_matchesPartialTitle() {
        movieRepository.save(new Movie(UUID.randomUUID(), 200L, "Batman: The Dark Knight", 2008, "Batman film", "/batman.jpg"));
        movieRepository.save(new Movie(UUID.randomUUID(), 201L, "Batman Begins", 2005, "Batman origin", "/begins.jpg"));

        var results = movieRepository.findByTitleContainingIgnoreCase("batman");

        assertThat(results).hasSize(2);
    }

    @Test
    void findByTitleContainingIgnoreCase_isCaseInsensitive() {
        movieRepository.save(new Movie(UUID.randomUUID(), 300L, "INCEPTION", 2010, "Dream heist", "/inception.jpg"));

        var results = movieRepository.findByTitleContainingIgnoreCase("inception");

        assertThat(results).hasSize(1);
    }
}
