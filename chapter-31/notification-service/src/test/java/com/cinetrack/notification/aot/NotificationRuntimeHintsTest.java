package com.cinetrack.notification.aot;

import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHints;
import static org.assertj.core.api.Assertions.assertThatCode;

class NotificationRuntimeHintsTest {

    @Test
    void registerHints_doesNotThrow() {
        var hints = new RuntimeHints();
        var registrar = new NotificationRuntimeHints();
        assertThatCode(() -> registrar.registerHints(hints, getClass().getClassLoader()))
                .doesNotThrowAnyException();
    }
}
