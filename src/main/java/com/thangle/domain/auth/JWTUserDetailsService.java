package com.thangle.domain.auth;

import com.thangle.persistence.role.RoleStore;
import com.thangle.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {

    private final UserStore userStore;
    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userStore.findUserByUsername(username)
                .map(this::buildUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s cannot be found" + username));
    }

    private User buildUser(final com.thangle.domain.user.User user) {
        return new JWTUserDetails(user.getId(), user.getUsername(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(roleStore.getRoleById(user.getRoleId()))));
    }
}
