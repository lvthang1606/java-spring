package com.thangle.domain.auth;

import com.thangle.persistence.properties.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static com.thangle.fakes.JWTUserDetailsFakes.buildJWTUserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JWTTokenServiceTest {

    private static final String SECRET = "testSecret";

    private static final Long EXPIRATION = 3600L;

    @Mock
    private JWTProperties jwtProperties;

    @InjectMocks
    private JWTTokenService jwtTokenService;

    @Test
    void shouldParseWithEmptyToken_thenReturnsNull() {
        assertNull(jwtTokenService.parse(""));
    }

    @Test
    void shouldParseWithNullToken_thenReturnsNull() {
        assertNull(jwtTokenService.parse(""));
    }

    @Test
    public void shouldGenerateToken_OK() {
        final JWTUserDetails jwtUserDetails = buildJWTUserDetails();

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(jwtUserDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        final Date expiration = claims.getExpiration();
        final Date issuedAt = claims.getIssuedAt();

        assertEquals(jwtUserDetails.getUsername(), claims.getSubject());
        assertEquals("ROLE_USER", claims.get("roles").toString());
        assertTrue(expiration.toInstant().isAfter(issuedAt.toInstant()));
        assertEquals(expiration.getTime() - issuedAt.getTime(), EXPIRATION * 1000);

        verify(jwtProperties).getSecret();
        verify(jwtProperties).getExpiration();
    }
}
