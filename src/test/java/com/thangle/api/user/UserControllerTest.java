package com.thangle.api.user;

import com.thangle.api.AbstractControllerTest;
import com.thangle.domain.user.UserService;
import com.thangle.domain.user.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.thangle.fakes.UserFakes.buildUsers;
import static com.thangle.fakes.UserFakes.buildUser;
import static com.thangle.api.user.UserResponseDTOMapper.toUserResponseDTO;
import static com.thangle.api.user.UserRequestDTOMapper.toUserRequestDTO;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldFindAll_OK() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName()))
                .andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(users.get(0).getRoleId().toString()));

        verify(userService).findAll();
    }

    @Test
    void shouldFindById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        get(BASE_URL + "/" + user.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findById(user.getId());
    }

    @Test
    void shouldCreate_OK() throws Exception{
        final var user = buildUser();
        final var userRequestDTO = toUserRequestDTO(user);

        when(userService.create(argThat(x -> x.getUsername().equals(userRequestDTO.getUsername())))).thenReturn(user);

        post(BASE_URL, userRequestDTO)
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

    }

    @Test
    void shouldUpdate_OK() throws Exception {
        final var userNeedsToBeUpdated = buildUser();
        final var updatedUser = buildUser().withId(userNeedsToBeUpdated.getId());
        final var userRequestDTO = toUserRequestDTO(updatedUser);

        when(userService.update(eq(userNeedsToBeUpdated.getId()), argThat(x -> x.getUsername().equals(userRequestDTO.getUsername()))))
                .thenReturn(updatedUser);

        put(BASE_URL + "/" + userNeedsToBeUpdated.getId(), userRequestDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId().toString()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.enabled").value(updatedUser.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(updatedUser.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(updatedUser.getRoleId().toString()));

        verify(userService).update(any(UUID.class), any(User.class));
    }

    @Test
    void shouldDeleteById_OK() throws Exception {
        final var user = buildUser();

        delete(BASE_URL + "/" + user.getId())
                .andExpect(status().isOk());

        verify(userService).delete(user.getId());
    }
}
