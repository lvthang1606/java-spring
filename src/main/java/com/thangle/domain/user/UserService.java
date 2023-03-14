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
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findById(final UUID id) {
        return userStore.findById(id).orElseThrow(supplyUserNotFound(id));
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> optionalUser = userStore.findByUsername(username);
        if (optionalUser.isPresent()) {
            throw new BadRequestException("The username %s already exists", optionalUser.get().getUsername());
        }
    }

    public User create(final User user) {
        verifyUsernameAvailable(user.getUsername());
        return userStore.create(user);
    }

    public User update(final UUID id, final User updatedUser) {
        final User user = findById(id);

        if (isNotBlank(updatedUser.getUsername())) {
            verifyUsernameAvailable(updatedUser.getUsername());
            user.setUsername(updatedUser.getUsername());
        }

        if (isBlank(updatedUser.getPassword())) {
            throw new BadRequestException("Password cannot be empty");
        }

        user.setPassword(updatedUser.getPassword());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEnabled(updatedUser.getEnabled());
        user.setAvatar(updatedUser.getAvatar());
        user.setUpdatedAt(Instant.now());

        return userStore.update(user);
    }

    public void delete(final UUID id) {
        final User user = findById(id);
        userStore.delete(user.getId());
    }
}
