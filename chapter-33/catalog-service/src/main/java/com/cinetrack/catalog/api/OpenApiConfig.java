package com.cinetrack.catalog.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/movies/**")
                .build();
    }

    @Bean
    public GroupedOpenApi v2Api() {
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/api/v2/movies/**")
                .build();
    }

    @Bean
    public OpenAPI cineTrackOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("CinéTrack Catalog API")
                        .version("2.0")
                        .description("Movie catalog service API"));
    }
}
