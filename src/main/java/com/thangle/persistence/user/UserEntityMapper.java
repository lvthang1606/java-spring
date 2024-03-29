package com.thangle.persistence.user;

import com.thangle.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class UserEntityMapper {

    public static User toUser(final UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .enabled(userEntity.isEnabled())
                .avatar(userEntity.getAvatar())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .roleId(userEntity.getRoleId())
                .build();
    }

    public static UserEntity toUserEntity(final User user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roleId(user.getRoleId())
                .build();
    }

    public static List<User> toUsers(final List<UserEntity> userEntities) {
        return emptyIfNull(userEntities)
                .stream()
                .map(UserEntityMapper::toUser)
                .toList();
    }
}
