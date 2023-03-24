package com.thangle.persistence.user;

import com.thangle.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.collections4.IterableUtils.toList;
import static com.thangle.persistence.user.UserEntityMapper.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private List<User> fakeUsers;

    @PostConstruct
    public void init() {
        fakeUsers = List.of(
                User.builder()
                        .id(UUID.fromString("aaaaaaaa-3e86-4ad6-bcd8-f78b0fd263f8"))
                        .username("user1")
                        .password(passwordEncoder.encode("123"))
                        .roleId(UUID.fromString("dddddddd-3e86-4ad6-bcd8-f78b0fd263f8"))
                        .build(),
                User.builder()
                        .id(UUID.fromString("bbbbbbbb-3e86-4ad6-bcd8-f78b0fd263f8"))
                        .username("user2")
                        .password(passwordEncoder.encode("123"))
                        .roleId(UUID.fromString("eeeeeeee-be2e-4f19-b48e-2c52f189b9dd"))
                        .build()
        );
    }

    public List<User> findAll() {
        return toUsers(toList(userRepository.findAll()));
    }

    public Optional<User> findById(final UUID id) {
        return userRepository.findById(id).map(UserEntityMapper::toUser);
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username).map(UserEntityMapper::toUser);
    }

    public Optional<User> findUserByUsername(final String username) {
        return fakeUsers.stream()
                .filter(user -> equalsIgnoreCase(user.getUsername(), username))
                .findFirst();
    }

    public User save(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }
}
