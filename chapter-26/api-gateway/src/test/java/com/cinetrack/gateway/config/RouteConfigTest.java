package com.cinetrack.gateway.config;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RouteConfigTest {

    @Test
    void routeConfig_canBeInstantiated() {
        var config = new RouteConfig();
        assertThat(config).isNotNull();
    }
}
