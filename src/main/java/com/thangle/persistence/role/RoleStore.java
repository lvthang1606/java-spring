package com.thangle.persistence.role;

import com.thangle.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.thangle.persistence.role.RoleEntityMapper.toRole;
import static com.thangle.domain.role.RoleError.supplyRoleNotFound;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public Role findById(final UUID id) {
        return toRole(roleRepository.findById(id).orElseThrow(supplyRoleNotFound(id)));
    }
}
