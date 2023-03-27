package com.thangle.fakes;

import com.thangle.domain.auth.JWTUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static java.util.Collections.singleton;
import static java.util.UUID.randomUUID;

public class JWTUserDetailsFakes {

    public static JWTUserDetails buildJWTUserDetails() {
        return new JWTUserDetails(randomUUID(),
                randomAlphabetic(3, 10),
                randomAlphabetic(3, 10),
                singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
