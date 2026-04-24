package com.cinetrack.user.application;

import com.cinetrack.common.exception.ResourceNotFoundException;
import com.cinetrack.user.domain.AppUser;
import com.cinetrack.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findById_whenUserExists_returnsResponse() {
        var id = UUID.randomUUID();
        var user = new AppUser(id, "alice@example.com", "Alice", "https://avatar.example.com/alice");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        var response = userService.findById(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.email()).isEqualTo("alice@example.com");
        assertThat(response.displayName()).isEqualTo("Alice");
    }

    @Test
    void findById_whenUserNotFound_throwsResourceNotFoundException() {
        var id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void register_savesNewUser_andReturnsResponse() {
        var request = new RegisterUserRequest("bob@example.com", "Bob");
        var savedUser = new AppUser(UUID.randomUUID(), "bob@example.com", "Bob", null);
        when(userRepository.save(any(AppUser.class))).thenReturn(savedUser);

        var response = userService.register(request);

        assertThat(response.email()).isEqualTo("bob@example.com");
        assertThat(response.displayName()).isEqualTo("Bob");
    }

    @Test
    void register_whenEmailAlreadyTaken_throwsDuplicateEmailException() {
        var request = new RegisterUserRequest("taken@example.com", "User");
        when(userRepository.existsByEmail("taken@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(DuplicateEmailException.class);
    }
}
