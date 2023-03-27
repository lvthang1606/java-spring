package com.thangle.error;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends DomainException {

    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "You are not authorized to perform this action");
    }
}
