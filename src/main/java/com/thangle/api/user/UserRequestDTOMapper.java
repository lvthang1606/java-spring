package com.thangle.api.user;

import com.thangle.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserRequestDTOMapper {

    public static User toUser(final UserRequestDTO userRequestDTO) {
        return User.builder()
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .enabled(userRequestDTO.isEnabled())
                .avatar(userRequestDTO.getAvatar())
                .roleId(userRequestDTO.getRoleId())
                .build();
    }

    public static UserRequestDTO toUserRequestDTO(final User user) {
        return UserRequestDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .roleId(user.getRoleId())
                .build();
    }
}
