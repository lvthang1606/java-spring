package com.thangle.api;

import com.thangle.error.DomainException;
import com.thangle.error.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class ControllerErrorHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DomainException.class})
    public ResponseEntity<ErrorDTO> handleDomainError(final DomainException error) {
        final var errorDTO = ErrorDTO.builder()
                .message(error.getMessage())
                .occurAt(Instant.now())
                .build();

        return ResponseEntity
                .status(error.getHttpStatus())
                .body(errorDTO);
    }
}
