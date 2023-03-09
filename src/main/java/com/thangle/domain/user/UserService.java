package com.thangle.domain.user;

import com.thangle.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.thangle.persistence.user.UserStore;
import static com.thangle.domain.user.UserError.supplyUserNotFound;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findUserById(final UUID id) {
        return userStore.findUserById(id).orElseThrow(supplyUserNotFound(id));
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> optionalUser = userStore.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            throw new BadRequestException("User already exists");
        }
    }

    public User createUser(final User user) {
        verifyUsernameAvailable(user.getUsername());
        return userStore.createUser(user);
    }

    public User updateUser(final UUID id, final User updatedUser) {
        final User user = findUserById(id);

        verifyUsernameAvailable(updatedUser.getUsername());
        if (updatedUser.getPassword() == null || updatedUser.getPassword().toString() == "") {
            throw new BadRequestException("Password cannot be null");
        }
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEnabled(updatedUser.getEnabled());
        user.setAvatar(updatedUser.getAvatar());
        user.setUpdatedAt(Instant.now());

        return userStore.updateUser(user);
    }

    public void deleteUser(final UUID id) {
        final User user = findUserById(id);
        userStore.deleteUser(user.getId());
    }
}
