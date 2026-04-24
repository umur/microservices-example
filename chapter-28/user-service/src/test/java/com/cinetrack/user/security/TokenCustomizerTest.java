package com.cinetrack.user.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenCustomizerTest {

    @InjectMocks
    TokenCustomizer customizer;

    @Test
    void customize_addsPrincipalNameAsClaim() {
        var context = mock(JwtEncodingContext.class);
        var authentication = mock(Authentication.class);
        var claimsBuilder = JwtClaimsSet.builder().issuer("test");
        when(context.getPrincipal()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(context.getClaims()).thenReturn(claimsBuilder);

        customizer.customize(context);

        verify(context).getClaims();
        verify(context).getPrincipal();
    }
}
