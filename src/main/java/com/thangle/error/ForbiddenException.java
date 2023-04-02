package com.thangle.error;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends DomainException {

    public ForbiddenException(final String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
