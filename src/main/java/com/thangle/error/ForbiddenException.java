package com.thangle.error;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends DomainException {

    public ForbiddenException(final String message, final Object... args) {
        super(HttpStatus.FORBIDDEN, message, args);
    }
}
