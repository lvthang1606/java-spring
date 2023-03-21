package com.thangle.api.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.thangle.api.user.UserResponseDTOMapper.toUserResponseDTO;
import static com.thangle.api.user.UserResponseDTOMapper.toUserResponseDTOs;
import static com.thangle.fakes.UserFakes.buildUser;
import static com.thangle.fakes.UserFakes.buildUsers;

public class UserResponseDTOMapperTest {

    @Test
    void shouldToUserResponseDTO_OK() {
        final var user = buildUser();
        final var userResponseDTO = toUserResponseDTO(user);

        assertEquals(user.getId(), userResponseDTO.getId());
        assertEquals(user.getUsername(), userResponseDTO.getUsername());
        assertEquals(user.getFirstName(), userResponseDTO.getFirstName());
        assertEquals(user.getLastName(), userResponseDTO.getLastName());
        assertEquals(user.getAvatar(), userResponseDTO.getAvatar());
        assertEquals(user.getRoleId(), userResponseDTO.getRoleId());
    }

    @Test
    void shouldToUserResponseDTOs_OK() {
        final var users = buildUsers();
        final var userResponseDTOs = toUserResponseDTOs(users);

        assertEquals(userResponseDTOs.size(), users.size());
    }
}
