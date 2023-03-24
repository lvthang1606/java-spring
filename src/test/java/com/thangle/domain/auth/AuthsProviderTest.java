package com.thangle.domain.auth;

import com.thangle.error.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.thangle.fakes.UserAuthenticationTokenFakes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthsProviderTest {

    @InjectMocks
    private AuthsProvider authsProvider;

    @Test
    void shouldGetCurrentAuthentication_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(user, actual);
    }

    @Test
    void shouldGetCurrentAuthentication_ThrowsUnauthorizedException() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentAuthentication());
    }

    @Test
    void shouldGetCurrentUserRole_OK() {
        final var user = buildContributor();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentUserRole();

        assertEquals(user.getRole(), actual);
    }

    @Test
    void shouldGetCurrentUserId_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentUserId();

        assertEquals(user.getUserId(), actual);
    }
}
