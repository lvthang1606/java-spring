package com.thangle.api.user;

import org.junit.jupiter.api.Test;

import static com.thangle.fakes.UserFakes.buildUser;
import static com.thangle.api.user.UserUpdateDTOMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUpdateDTOMapperTest {
    @Test
    void shouldToUserUpdateDTO_OK() {
        final var user = buildUser();
        final var userUpdateDTO = toUserUpdateDTO(user);

        assertEquals(user.getUsername(), userUpdateDTO.getUsername());
        assertEquals(user.getPassword(), userUpdateDTO.getPassword());
        assertEquals(user.getFirstName(), userUpdateDTO.getFirstName());
        assertEquals(user.getLastName(), userUpdateDTO.getLastName());
        assertEquals(user.getEnabled(), userUpdateDTO.getEnabled());
        assertEquals(user.getAvatar(), userUpdateDTO.getAvatar());
        assertEquals(user.getRoleId(), userUpdateDTO.getRoleId());
    }
}
