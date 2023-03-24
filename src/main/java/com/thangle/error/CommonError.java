package com.thangle.error;

import java.util.function.Supplier;

public class CommonError {

    public static Supplier<ForbiddenException> supplyForbiddenError(final String message) {
        return () -> new ForbiddenException(message);
    }

    public static Supplier<AccessDeniedException> supplyAccessDeniedError(final String message) {
        return () -> new AccessDeniedException(message);
    }
}
