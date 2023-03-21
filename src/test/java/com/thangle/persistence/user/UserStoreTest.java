package com.thangle.persistence.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.thangle.fakes.UserFakes.*;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static com.thangle.persistence.user.UserEntityMapper.*;

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
    void shouldFindById_OK() {
        final var userEntity = buildUserEntity();
        final var foundUserEntity = buildUserEntity().withId(userEntity.getId());

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(foundUserEntity));

        final var actual = userStore.findById(userEntity.getId());
        final var expected = toUser(foundUserEntity);

        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected.getUsername(), actual.get().getUsername());
        assertEquals(expected.getPassword(), actual.get().getPassword());
        assertEquals(expected.getFirstName(), actual.get().getFirstName());
        assertEquals(expected.getLastName(), actual.get().getLastName());
        assertEquals(expected.getAvatar(), actual.get().getAvatar());
        assertEquals(expected.getRoleId(), actual.get().getRoleId());
    }

    @Test
    void shouldFindByUserName_OK() {
        final var userEntity = buildUserEntity();
        final var foundUserEntity = buildUserEntity().withUsername(userEntity.getUsername());

        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(foundUserEntity));

        final var actual = userStore.findByUsername(userEntity.getUsername());
        final var expected = toUser(foundUserEntity);

        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected.getUsername(), actual.get().getUsername());
        assertEquals(expected.getPassword(), actual.get().getPassword());
        assertEquals(expected.getFirstName(), actual.get().getFirstName());
        assertEquals(expected.getLastName(), actual.get().getLastName());
        assertEquals(expected.getAvatar(), actual.get().getAvatar());
        assertEquals(expected.getRoleId(), actual.get().getRoleId());
    }

    @Test
    void shouldSave_OK() {
        final var expected = buildUserEntity();
        when(userRepository.save(any())).thenReturn(expected);

        final var actual = userStore.save(buildUser());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.getRoleId(), actual.getRoleId());
    }

    @Test
    void shouldDelete_OK() {
        final var user = buildUserEntity();
        userStore.delete(user.getId());
    }
}
