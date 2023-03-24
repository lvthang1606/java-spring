package com.thangle.persistence.role;

import org.junit.jupiter.api.Test;

import static com.thangle.fakes.RoleFakes.buildRoleEntity;
import static com.thangle.persistence.role.RoleEntityMapper.toRole;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleEntityMapperTest {

    @Test
    void shouldToRole_OK() {
        final var roleEntity = buildRoleEntity();
        final var role = toRole(roleEntity);

        assertEquals(roleEntity.getId(), role.getId());
        assertEquals(roleEntity.getName(), role.getName());
    }
}
