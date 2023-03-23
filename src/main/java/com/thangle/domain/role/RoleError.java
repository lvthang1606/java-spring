package com.thangle.domain.role;

import com.thangle.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class RoleError {

    public static Supplier<NotFoundException> supplyRoleNotFound(final UUID id) {
        return () -> new NotFoundException("Role with id %s could not be found", id);
    }
}
