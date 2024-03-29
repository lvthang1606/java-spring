package com.thangle.persistence.role;

import com.thangle.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public Optional<Role> findById(final UUID id) {
        return roleRepository.findById(id).map(RoleEntityMapper::toRole);
    }
}
