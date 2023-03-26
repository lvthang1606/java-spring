package com.thangle.domain.auth;

import com.thangle.persistence.role.RoleStore;
import com.thangle.persistence.user.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static com.thangle.fakes.UserFakes.buildUser;
import static com.thangle.fakes.RoleFakes.buildRole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class JWTUserDetailsServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private RoleStore roleStore;

    @InjectMocks
    private JWTUserDetailsService jwtUserDetailsService;

    @BeforeEach
    public void setup() {
        openMocks(this);
        jwtUserDetailsService = new JWTUserDetailsService(userStore, roleStore);
    }

    @Test
    void shouldLoadUserByUsername_OK() {
        final var user = buildUser();
        final var role = buildRole().withId(user.getRoleId());

        when(userStore.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleStore.findById(role.getId())).thenReturn(Optional.of(role));

        final UserDetails actual = jwtUserDetailsService.loadUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), actual.getUsername());

        verify(userStore).findByUsername(anyString());
        verify(roleStore).findById(role.getId());
    }

    @Test
    void shouldLoadUserByUsername_ThrowsUsernameNotFoundException() {
        final var username = randomAlphabetic(3, 10);

        when(userStore.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(username));

        verify(userStore).findByUsername(username);
    }
}
