package com.thangle.domain.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class JWTUserDetails extends User {

    private final UUID userId;

    public JWTUserDetails(final UUID userId,
                          final String username,
                          final String password,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }
}
