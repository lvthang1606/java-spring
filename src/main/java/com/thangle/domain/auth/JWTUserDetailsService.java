package com.thangle.domain.auth;

import com.thangle.domain.role.Role;
import com.thangle.error.NotFoundException;
import com.thangle.persistence.role.RoleStore;
import com.thangle.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.thangle.domain.role.RoleError.supplyRoleNotFound;

@Service
@RequiredArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {

    private final UserStore userStore;
    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(final String username) throws NotFoundException {
        return userStore.findByUsername(username)
                .map(this::buildUser)
                .orElseThrow(() -> new NotFoundException("User with username %s cannot be found", username));
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
