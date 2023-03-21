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
import static org.apache.commons.lang3.StringUtils.*;

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
        user.setCreatedAt(Instant.now());
        return userStore.save(user);
    }

    private String setFieldValue(final String oldValue, final String newValue) {
        if (isNotBlank(newValue)) {
            return newValue;
        }

        return oldValue;
    }

    public User update(final UUID id, final User updatedUser) {
        final User user = findById(id);

        if (isNotBlank(updatedUser.getUsername())) {
            verifyUsernameAvailable(updatedUser.getUsername());
            user.setUsername(updatedUser.getUsername());
        }

        user.setPassword(setFieldValue(user.getPassword(), updatedUser.getPassword()));
        user.setFirstName(setFieldValue(user.getFirstName(), updatedUser.getFirstName()));
        user.setLastName(setFieldValue(user.getLastName(), updatedUser.getLastName()));
        user.setAvatar(setFieldValue(user.getAvatar(), updatedUser.getAvatar()));

        if (updatedUser.getEnabled() != null) {
            user.setEnabled(updatedUser.getEnabled());
        }

        user.setUpdatedAt(Instant.now());

        return userStore.save(user);
    }

    public void delete(final UUID id) {
        final User user = findById(id);
        userStore.delete(user.getId());
    }
}
