package com.thangle.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ValidationException(final HttpStatus httpStatus, final String message, Object... args) {
        super(String.format(message, args));
        this.httpStatus = httpStatus;
    }
}
