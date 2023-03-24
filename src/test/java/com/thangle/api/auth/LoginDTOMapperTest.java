package com.thangle.api.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginDTOMapperTest {
    @Test
    void shouldToAuthentication_OK() {
        LoginDTO loginDTO = LoginDTO.builder()
                .username("admin")
                .password("123")
                .build();

        Authentication authentication = LoginDTOMapper.toAuthentication(loginDTO);

        assertEquals(loginDTO.getUsername(), authentication.getPrincipal());
        assertEquals(loginDTO.getPassword(), authentication.getCredentials());
    }
}
