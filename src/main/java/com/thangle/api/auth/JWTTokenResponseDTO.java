package com.thangle.api.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JWTTokenResponseDTO {

    private final String token;
}
