package com.thangle.error;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends DomainException {

    public ForbiddenException(final Object... args) {
        super(HttpStatus.FORBIDDEN, "You do not have permission to access this resource", args);
    }
}
