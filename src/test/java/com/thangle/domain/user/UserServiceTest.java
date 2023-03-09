package com.thangle.domain.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static com.thangle.fakes.UserFakes.buildUser;
import static com.thangle.fakes.UserFakes.buildUsers;
import com.thangle.persistence.user.UserStore;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {
    @Mock
    private UserStore userStore;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindAll_OK(){
        final var expected = buildUsers();

        when(userStore.findAll()).thenReturn(expected);

        final var actual = userService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getPassword(), actual.get(0).getPassword());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());

        verify(userStore).findAll();
    }

    @Test
    void shouldCreateUser_OK() {
        final var user = buildUser();
        when(userStore.createUser(user)).thenReturn(user);

        final var createdUser = userService.createUser(user);

        assertEquals(user, createdUser);
        verify(userStore).createUser(user);
    }

    @Test
    void shouldUpdateUser_OK() {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setId(user.getId());

        when(userStore.findUserById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.updateUser(user)).thenReturn(updatedUser);

        final var expected = userService.updateUser(user.getId(), user);

        assertEquals(expected.getId(), updatedUser.getId());
        assertEquals(expected.getUsername(), updatedUser.getUsername());
        assertEquals(expected.getPassword(), updatedUser.getPassword());
        assertEquals(expected.getFirstName(), updatedUser.getFirstName());
        assertEquals(expected.getLastName(), updatedUser.getLastName());
        assertEquals(expected.getAvatar(), updatedUser.getAvatar());
        assertEquals(expected.getRoleId(), updatedUser.getRoleId());
    }

    @Test
    void shouldDeleteById_OK() {
        final var user = buildUser();

        when(userStore.findUserById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());
        verify(userStore).deleteUser(user.getId());
    }
}