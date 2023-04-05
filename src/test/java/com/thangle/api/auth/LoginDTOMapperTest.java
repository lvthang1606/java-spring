package com.thangle.api.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.thangle.fakes.LoginFakes.buildLoginDTO;

public class LoginDTOMapperTest {
    @Test
    void shouldToAuthentication_OK() {
        final LoginDTO loginDTO = buildLoginDTO();

        final Authentication authentication = LoginDTOMapper.toAuthentication(loginDTO);

        assertEquals(loginDTO.getUsername(), authentication.getPrincipal());
        assertEquals(loginDTO.getPassword(), authentication.getCredentials());
    }
}
