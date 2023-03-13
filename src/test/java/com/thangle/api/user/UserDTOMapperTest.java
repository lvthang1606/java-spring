package com.thangle.api.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.thangle.api.user.UserDTOMapper.toUserDTO;
import static com.thangle.api.user.UserDTOMapper.toUserDTOs;
import static com.thangle.fakes.UserFakes.buildUser;
import static com.thangle.fakes.UserFakes.buildUsers;

public class UserDTOMapperTest {
    @Test
    void shouldToUserDTO_OK() {
        final var user = buildUser();
        final var userDTO = toUserDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getAvatar(), userDTO.getAvatar());
        assertEquals(user.getRoleId(), userDTO.getRoleId());
    }

    @Test
    void shouldToUsersDTO_OK() {
        final var users = buildUsers();
        final var userDTOS = toUserDTOs(users);

        assertEquals(userDTOS.size(), users.size());
    }
}
