package com.thangle.domain.user;

import com.thangle.error.ValidationException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class UserError {
    public static Supplier<ValidationException> supplyUserNotFound(final UUID id) {
        return () -> new ValidationException(HttpStatus.NOT_FOUND, "User with id %s could not be found", id);
    }
}
