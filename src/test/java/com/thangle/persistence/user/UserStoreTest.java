package com.thangle.persistence.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.thangle.fakes.UserFakes.*;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserStoreTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserStore userStore;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildUserEntities();

        when(userRepository.findAll()).thenReturn(expected);

        assertEquals(expected.size(), userStore.findAll().size());

        verify(userRepository).findAll();
    }

    @Test
    void shouldCreate_OK() {
        final var expected = buildUserEntity();
        when(userRepository.save(any())).thenReturn(expected);

        final var actual = userStore.create(buildUser());

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getUsername(), expected.getUsername());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getAvatar(), expected.getAvatar());
        assertEquals(actual.getRoleId(), expected.getRoleId());
    }

    @Test
    void shouldUpdate_OK() {
        final var expected = buildUserEntity();
        when(userRepository.save(any())).thenReturn(expected);

        final var actual = userStore.update(buildUser());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getUsername(), expected.getUsername());
        assertEquals(actual.getPassword(), expected.getPassword());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getAvatar(), expected.getAvatar());
        assertEquals(actual.getRoleId(), expected.getRoleId());
    }

    @Test
    void shouldDelete_OK() {
        final var user = buildUserEntity();
        userStore.delete(user.getId());

    }
}
