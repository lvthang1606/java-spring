package com.thangle.domain.auth;

import com.thangle.persistence.properties.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class JWTTokenServiceTest {

    private static final String SECRET = "testSecret";

    private static final Long EXPIRATION = 3600L;

    private JWTTokenService jwtTokenService;

    @Mock
    private JWTProperties jwtProperties;

    @BeforeEach
    void setup() {
        openMocks(this);
        jwtTokenService = new JWTTokenService(jwtProperties);
    }

    @Test
    public void shouldGenerateToken_OK() {
        final JWTUserDetails jwtUserDetails = new JWTUserDetails(UUID.randomUUID(),
                "user",
                "123",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(jwtUserDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        final Date expiration = claims.getExpiration();
        final Date issuedAt = claims.getIssuedAt();

        assertEquals("user", claims.getSubject());
        assertEquals("ROLE_USER", claims.get("roles").toString());
        assertTrue(expiration.toInstant().isAfter(issuedAt.toInstant()));
        assertEquals(expiration.getTime() - issuedAt.getTime(), EXPIRATION * 1000);
    }
}
