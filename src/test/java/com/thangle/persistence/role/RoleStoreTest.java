package com.thangle.persistence.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.thangle.fakes.RoleFakes.buildRoleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleStoreTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleStore roleStore;

    @Test
    void shouldFindById_OK() {
        final var expected = buildRoleEntity();

        when(roleRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
        
        final var actual = roleStore.findById(expected.getId());

        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected.getName(), actual.get().getName());

        verify(roleRepository).findById(expected.getId());
    }
}
