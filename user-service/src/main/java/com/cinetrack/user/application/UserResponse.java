package com.cinetrack.user.application;

import com.cinetrack.user.domain.AppUser;

import java.util.UUID;

public record UserResponse(UUID id, String email, String displayName, String avatarUrl) {
    public static UserResponse from(AppUser user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getDisplayName(), user.getAvatarUrl());
    }
}
