package com.thangle.domain.auth;

import com.thangle.error.BadRequestException;
import com.thangle.error.NotFoundException;
import com.thangle.persistence.role.RoleStore;
import com.thangle.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.thangle.fakes.AuthenticationFakes.buildAuthentication;
import static com.thangle.fakes.RoleFakes.buildRole;
import static com.thangle.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoginAuthenticationProviderTest {

    @Mock
    private UserStore userStore;

    @Mock
    private RoleStore roleStore;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Test
    void shouldAuthenticate_OK() {
        final var authentication = buildAuthentication();
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final var user = buildUser().withUsername(username);
        final var role = buildRole().withId(user.getRoleId());

        when(userStore.findByUsername(username)).thenReturn(Optional.of(user));
        when(roleStore.findById(role.getId())).thenReturn(Optional.of(role));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        final var actual = loginAuthenticationProvider.authenticate(authentication);

        assertEquals(username, actual.getName());
        assertEquals(password, actual.getCredentials().toString());
        assertEquals(authentication.getAuthorities(), actual.getAuthorities());
        assertEquals(authentication.getPrincipal(), actual.getPrincipal());

        verify(userStore).findByUsername(username);
        verify(passwordEncoder).matches(password, user.getPassword());
    }

    @Test
    void shouldAuthenticate_ThrowsBadRequestException() {
        final var authentication = buildAuthentication();
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final var user = buildUser().withUsername(username);
        final var role = buildRole().withId(user.getRoleId());

        when(userStore.findByUsername(username)).thenReturn(Optional.of(user));
        when(roleStore.findById(role.getId())).thenReturn(Optional.of(role));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> loginAuthenticationProvider.authenticate(authentication));

        verify(userStore).findByUsername(username);
        verify(roleStore).findById(role.getId());
        verify(passwordEncoder).matches(password, user.getPassword());
    }

    @Test
    void shouldAuthenticate_ThrowsNotFoundException() {
        final var authentication = buildAuthentication();
        final String username = authentication.getName();

        when(userStore.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> loginAuthenticationProvider.authenticate(authentication));

        verify(userStore).findByUsername(username);
    }
}
