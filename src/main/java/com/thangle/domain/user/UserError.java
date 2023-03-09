package com.thangle.domain.user;

import com.thangle.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class UserError {
    public static Supplier<NotFoundException> supplyUserNotFound(final UUID id) {
        return () -> new NotFoundException("User with id %s could not be found", id);
    }
}
