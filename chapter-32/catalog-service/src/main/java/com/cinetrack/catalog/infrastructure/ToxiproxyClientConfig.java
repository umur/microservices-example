package com.cinetrack.catalog.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToxiproxyClientConfig {

    @Bean
    @ConditionalOnProperty(name = "toxiproxy.enabled", havingValue = "true")
    public String toxiproxyHost() {
        return System.getenv().getOrDefault("TOXIPROXY_HOST", "localhost");
    }
}
