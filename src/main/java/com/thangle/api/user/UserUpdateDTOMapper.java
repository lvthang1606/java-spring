package com.thangle.api.user;

import com.thangle.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUpdateDTOMapper {
    public static User toUser(final UserUpdateDTO userUpdateDTO) {
        return User.builder()
                .username(userUpdateDTO.getUsername())
                .password(userUpdateDTO.getPassword())
                .firstName(userUpdateDTO.getFirstName())
                .lastName(userUpdateDTO.getLastName())
                .enabled(userUpdateDTO.getEnabled())
                .avatar(userUpdateDTO.getAvatar())
                .roleId(userUpdateDTO.getRoleId())
                .build();
    }

    public static UserUpdateDTO toUserUpdateDTO(final User user) {
        return UserUpdateDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.getEnabled())
                .avatar(user.getAvatar())
                .roleId(user.getRoleId())
                .build();
    }
}
