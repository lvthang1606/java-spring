package com.thangle.persistence.user;

import org.junit.jupiter.api.Test;

import static com.thangle.fakes.UserFakes.*;
import static com.thangle.persistence.user.UserEntityMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityMapperTest {

    @Test
    void shouldToUser_OK() {
        final var userEntity = buildUserEntity();
        final var user = toUser(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getFirstName(), user.getFirstName());
        assertEquals(userEntity.getLastName(), user.getLastName());
        assertEquals(userEntity.getAvatar(), user.getAvatar());
        assertEquals(userEntity.isEnabled(), user.isEnabled());
        assertEquals(userEntity.getRoleId(), user.getRoleId());
    }

    @Test
    void shouldToUsers_OK() {
        final var userEntity = buildUserEntities();
        final var users = toUsers(userEntity);

        assertEquals(userEntity.size(), users.size());
    }

    @Test
    void shouldToUserEntity_OK() {
        final var user = buildUser();
        final var userEntity = toUserEntity(user);

        assertEquals(user.getUsername(), userEntity.getUsername());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getFirstName(), userEntity.getFirstName());
        assertEquals(user.getLastName(), userEntity.getLastName());
        assertEquals(user.getAvatar(), userEntity.getAvatar());
        assertEquals(user.getRoleId(), userEntity.getRoleId());
    }
}
