package com.thangle.error;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ErrorDTO {

    private String message;
    private Instant occurAt;
}
