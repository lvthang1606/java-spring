package com.thangle.api.auth;

import com.thangle.api.AbstractControllerTest;
import com.thangle.domain.auth.JWTTokenService;
import com.thangle.domain.auth.JWTUserDetails;
import com.thangle.domain.auth.LoginAuthenticationProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static com.thangle.fakes.AuthenticationFakes.buildAuthentication;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/auths";

    @MockBean
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @MockBean
    private JWTTokenService jwtTokenService;

    @Test
    void shouldLogin_OK() throws Exception {
        final var auth = buildAuthentication();
        final var token = randomAlphabetic(3, 10);

        when(loginAuthenticationProvider.authenticate(any(Authentication.class))).thenReturn(auth);
        when(jwtTokenService.generateToken((JWTUserDetails) auth.getPrincipal())).thenReturn(token);

        post(BASE_URL, auth)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));

        verify(loginAuthenticationProvider).authenticate(any(Authentication.class));
        verify(jwtTokenService).generateToken((JWTUserDetails) auth.getPrincipal());
    }
}
