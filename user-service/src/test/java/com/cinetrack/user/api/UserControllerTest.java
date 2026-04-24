package com.cinetrack.user.api;

import com.cinetrack.user.application.RegisterUserRequest;
import com.cinetrack.user.application.UserResponse;
import com.cinetrack.user.application.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    @Test
    void getUser_returnsOkWithUserResponse() {
        UUID id = UUID.randomUUID();
        var response = new UserResponse(id, "alice@example.com", "Alice", null);
        when(userService.findById(id)).thenReturn(response);

        var result = controller.getUser(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().id()).isEqualTo(id);
        assertThat(result.getBody().email()).isEqualTo("alice@example.com");
    }

    @Test
    void register_returnsCreatedStatusWithBody() {
        var request = new RegisterUserRequest("bob@example.com", "Bob");
        UUID newId = UUID.randomUUID();
        var response = new UserResponse(newId, "bob@example.com", "Bob", null);
        when(userService.register(request)).thenReturn(response);

        var result = controller.register(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().email()).isEqualTo("bob@example.com");
        assertThat(result.getHeaders().getLocation()).isNotNull();
        assertThat(result.getHeaders().getLocation().toString())
                .isEqualTo("/api/users/" + newId);
    }
}
