package com.thangle;

import com.thangle.api.ControllerErrorHandling;
import com.thangle.error.DomainException;
import com.thangle.error.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerErrorHandlingTest {

    @Mock
    private DomainException domainException;

    @InjectMocks
    private ControllerErrorHandling controllerErrorHandling;

    @Test
    void shouldHandleDomainError_OK() {
        final var errorDTO = ErrorDTO.builder()
                .message(domainException.getMessage())
                .occurAt(Instant.now());

        when(domainException.getHttpStatus()).thenReturn(HttpStatus.valueOf(200));

        final var actual = controllerErrorHandling.handleDomainError(domainException);

        assertEquals(domainException.getHttpStatus(), actual.getStatusCode());
        assertEquals(errorDTO.build().getMessage(), actual.getBody().getMessage());
    }
}
