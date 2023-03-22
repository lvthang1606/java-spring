package com.thangle.api.user;

import org.junit.jupiter.api.Test;

import static com.thangle.api.user.UserRequestDTOMapper.*;
import static com.thangle.fakes.UserFakes.buildUserRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRequestDTOMapperTest {

    @Test
    void shouldToUser_OK() {
        final var userRequestDTO = buildUserRequestDTO();
        final var user = toUser(userRequestDTO);

        assertEquals(userRequestDTO.getUsername(), user.getUsername());
        assertEquals(userRequestDTO.getPassword(), user.getPassword());
        assertEquals(userRequestDTO.getFirstName(), user.getFirstName());
        assertEquals(userRequestDTO.getLastName(), user.getLastName());
        assertEquals(userRequestDTO.isEnabled(), user.isEnabled());
        assertEquals(userRequestDTO.getAvatar(), user.getAvatar());
        assertEquals(userRequestDTO.getRoleId(), user.getRoleId());
    }
}
