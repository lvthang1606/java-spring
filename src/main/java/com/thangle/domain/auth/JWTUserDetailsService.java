package com.thangle.domain.auth;

import com.thangle.domain.role.Role;
import com.thangle.persistence.role.RoleStore;
import com.thangle.persistence.user.UserEntity;
import com.thangle.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.thangle.persistence.user.UserEntityMapper.toUserEntity;

@Service
@RequiredArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {

    private final UserStore userStore;
    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userStore.findUserByUsername(username)
                .map(user -> buildUser(toUserEntity(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s cannot be found" + username));
    }

    private User buildUser(final UserEntity userEntity) {
        final Role role = roleStore.findById(userEntity.getRoleId());
        return new JWTUserDetails(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                List.of(new SimpleGrantedAuthority(role.getName())));
    }
}
