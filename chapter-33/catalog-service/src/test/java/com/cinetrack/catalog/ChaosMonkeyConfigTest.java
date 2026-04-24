package com.cinetrack.catalog;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ChaosMonkeyConfigTest {

    @Test
    void chaosMonkeyConfig_environmentVariableDefaultsToDisabled() {
        String enabled = System.getenv().getOrDefault("CHAOS_MONKEY_ENABLED", "false");
        assertThat(enabled).isEqualTo("false");
    }
}
