package com.thangle.api.user;

import com.thangle.api.AbstractControllerTest;
import com.thangle.api.WithMockAdmin;
import com.thangle.api.WithMockContributor;
import com.thangle.api.book.BookController;
import com.thangle.domain.auth.AuthsProvider;
import com.thangle.domain.user.UserService;
import com.thangle.domain.user.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.thangle.fakes.UserFakes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @MockBean
    private UserService userService;

    @MockBean
    private AuthsProvider authsProvider;

    @Test
    @WithMockAdmin
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
    @WithMockAdmin
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
    @WithMockAdmin
    void shouldCreate_OK() throws Exception{
        final var user = buildUser();
        final var userRequestDTO = buildUserRequestDTO();

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
    @WithMockAdmin
    void shouldUpdate_OK() throws Exception {
        final var userNeedsToBeUpdated = buildUser();
        final var updatedUser = buildUser().withId(userNeedsToBeUpdated.getId());
        final var userRequestDTO = buildUserRequestDTO();

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
    @WithMockAdmin
    void shouldDeleteById_OK() throws Exception {
        final var user = buildUser();

        delete(BASE_URL + "/" + user.getId())
                .andExpect(status().isOk());

        verify(userService).delete(user.getId());
    }
}
