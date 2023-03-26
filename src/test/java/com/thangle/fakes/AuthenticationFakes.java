package com.thangle.fakes;

import com.thangle.domain.auth.JWTUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class AuthenticationFakes {

    public static Authentication buildAuthentication() {
        JWTUserDetails userDetails = new JWTUserDetails(
                UUID.randomUUID(),
                randomAlphabetic(3, 10),
                randomAlphabetic(3, 10),
                emptyList()
        );
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }
}
