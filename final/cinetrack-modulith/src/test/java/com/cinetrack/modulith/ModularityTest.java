package com.cinetrack.modulith;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {

    @Test
    void modulesAreStructuredCorrectly() {
        var modules = ApplicationModules.of(CineTrackModulithApplication.class);
        modules.verify();
    }
}
