package com.thangle.error;

import java.util.function.Supplier;

public class CommonError {

    public static Supplier<ForbiddenException> supplyForbiddenError() {
        return () -> new ForbiddenException("You do not have permission to access this resource");
    }
}
