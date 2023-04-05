package com.thangle.persistence.role;

import com.thangle.domain.role.Role;

public class RoleEntityMapper {

    public static Role toRole(final RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }
}
