package com.thangle.domain.auth;

import com.thangle.domain.role.Role;
import com.thangle.error.BadRequestException;
import com.thangle.error.NotFoundException;
import com.thangle.persistence.role.RoleStore;
import com.thangle.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.thangle.domain.role.RoleError.supplyRoleNotFound;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserStore userStore;

    private final RoleStore roleStore;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws RuntimeException {
        final Object credentials = authentication.getCredentials();

        if(!(credentials instanceof String)) {
            return null;
        }

        final String username = authentication.getName();
        final String password = credentials.toString();

        final var optionalUser = userStore.findByUsername(username);

        optionalUser
                .map(this::buildUser)
                .orElseThrow(() -> new NotFoundException("User with username %s cannot be found", username));

        if (!passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new BadRequestException("Wrong username of password");
        }

        UserDetails userDetails = buildUser(optionalUser.get());

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                authentication.getCredentials(),
                authentication.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private UserDetails buildUser(final com.thangle.domain.user.User user) {
        final var roleId = user.getRoleId();
        final Role role = roleStore.findById(roleId).orElseThrow(supplyRoleNotFound(roleId));
        return new JWTUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role.getName())));
    }
}
