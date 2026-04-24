package com.cinetrack.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("catalog-service", r -> r
                        .path("/api/movies/**")
                        .uri("lb://catalog-service"))
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("lb://user-service"))
                .route("catalog-service-legacy", r -> r
                        .path("/api/v1/movies/**")
                        .filters(f -> f.rewritePath("/api/v1/movies/(?<segment>.*)", "/api/movies/${segment}"))
                        .uri("lb://catalog-service"))
                .route("watchlist-service-v2", r -> r
                        .path("/api/watchlist/**")
                        .and()
                        .header("X-Feature-Flag", "watchlist-v2")
                        .uri("lb://watchlist-service"))
                .route("watchlist-service", r -> r
                        .path("/api/watchlist/**")
                        .uri("lb://watchlist-service"))
                .build();
    }
}
