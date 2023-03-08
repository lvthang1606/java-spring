package com.thangle.persistence.user;

import com.thangle.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.collections4.IterableUtils.toList;
import static com.thangle.persistence.user.UserEntityMapper.*;

@Repository
@RequiredArgsConstructor
public class UserStore {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return toUsers(toList(userRepository.findAll()));
    }

    public Optional<User> findUserById(final UUID id) {
        return userRepository.findById(id).map(UserEntityMapper::toUser);
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(UserEntityMapper::toUser);
    }

    public User createUser(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public User updateUser(final User updatedUser) {
        return toUser(userRepository.save(toUserEntity(updatedUser)));
    }

    public void deleteUser(final UUID id) {
        userRepository.deleteById(id);
    }
}
