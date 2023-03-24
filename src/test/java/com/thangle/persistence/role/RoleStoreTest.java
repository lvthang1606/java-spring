package com.thangle.persistence.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.thangle.fakes.RoleFakes.buildRoleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleStoreTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleStore roleStore;

    @Test
    void shouldFindById_OK() {
        final var role = buildRoleEntity();
        final var expected = buildRoleEntity().withId(role.getId());

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(expected));
        
        final var actual = roleStore.findById(role.getId());

        assertEquals(expected.getId(), actual.get().getId());
        assertEquals(expected.getName(), actual.get().getName());
    }
}
