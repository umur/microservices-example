package com.cinetrack.notification.aot;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.stereotype.Component;

@Component
@ImportRuntimeHints(NotificationRuntimeHints.class)
public class NotificationRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("templates/*");
        hints.serialization().registerType(
                org.springframework.aot.hint.TypeReference.of(com.cinetrack.common.event.ReviewPosted.class));
    }
}
