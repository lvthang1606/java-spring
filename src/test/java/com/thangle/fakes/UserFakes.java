package com.thangle.fakes;

import com.thangle.domain.user.User;
import com.thangle.persistence.user.UserEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserFakes {
    public static User buildUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username(randomAlphabetic(3, 10))
                .password(randomAlphabetic(3, 10))
                .firstName(randomAlphabetic(3, 10))
                .lastName(randomAlphabetic(3, 10))
                .avatar(randomAlphabetic(3, 10))
                .enabled(Boolean.TRUE)
                .roleId(UUID.randomUUID())
                .build();
    }

    public static List<User> buildUsers() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildUser())
                .toList();
    }

    public static UserEntity buildUserEntity() {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .username(randomAlphabetic(3, 10))
                .password(randomAlphabetic(3, 10))
                .firstName(randomAlphabetic(3, 10))
                .lastName(randomAlphabetic(3, 10))
                .avatar(randomAlphabetic(3, 10))
                .enabled(Boolean.TRUE)
                .roleId(UUID.randomUUID())
                .build();
    }

    public static List<UserEntity> buildUserEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildUserEntity())
                .toList();
    }
}
