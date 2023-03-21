package com.thangle.domain.user;

import com.thangle.error.BadRequestException;
import com.thangle.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldFindById_OK() {
        final var user = buildUser();
        final var expected = buildUser().withId(user.getId());

        when(userStore.findById(user.getId())).thenReturn(Optional.of(expected));

        final var actual = userService.findById(user.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEnabled(), actual.getEnabled());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
    }

    @Test
    void shouldFindById_ThrowsNotFoundException() {
        final var user = buildUser();

        when(userStore.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(user.getId()));
    }

    @Test
    void shouldCreate_OK() {
        final var user = buildUser();
        when(userStore.save(user)).thenReturn(user);

        final var createdUser = userService.create(user);

        assertEquals(user, createdUser);
        verify(userStore).save(user);
    }

    @Test
    void shouldCreate_ThrowsBadRequestException() {
        final var user = buildUser();
        final var foundUser = buildUser().withUsername(user.getUsername());

        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(foundUser));

        assertThrows(BadRequestException.class, () -> userService.create(user));
    }

    @Test
    void shouldUpdate_OK() {
        final var user = buildUser();
        final var updatedUser = buildUser().withId(user.getId());

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.save(user)).thenReturn(updatedUser);

        final var expected = userService.update(user.getId(), user);

        assertEquals(expected.getId(), updatedUser.getId());
        assertEquals(expected.getUsername(), updatedUser.getUsername());
        assertEquals(expected.getPassword(), updatedUser.getPassword());
        assertEquals(expected.getFirstName(), updatedUser.getFirstName());
        assertEquals(expected.getLastName(), updatedUser.getLastName());
        assertEquals(expected.getAvatar(), updatedUser.getAvatar());
        assertEquals(expected.getRoleId(), updatedUser.getRoleId());
    }

    @Test
    void shouldUpdate_ThrowsBadRequestException() {
        final var user = buildUser();
        final var foundUser = buildUser().withUsername(user.getUsername());

        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(foundUser));

        assertThrows(BadRequestException.class, () -> userService.create(user));
    }

    @Test
    void shouldDeleteById_OK() {
        final var user = buildUser();

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));

        userService.delete(user.getId());
        verify(userStore).delete(user.getId());
    }
}