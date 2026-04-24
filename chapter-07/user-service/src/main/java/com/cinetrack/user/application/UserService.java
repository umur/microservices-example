package com.cinetrack.user.application;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.user.domain.AppUser;
import com.cinetrack.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse findById(UUID id) {
        return userRepository.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }
        var user = new AppUser(UUID.randomUUID(), request.email(), request.displayName(), null);
        return UserResponse.from(userRepository.save(user));
    }
}
