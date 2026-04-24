package com.cinetrack.common.event;

import java.time.Instant;

public record UserRegistered(
        String userId,
        String email,
        String displayName,
        Instant occurredAt
) {}
