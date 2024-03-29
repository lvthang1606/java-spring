package com.thangle.persistence.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JWTProperties {

    @NotBlank
    private String secret;

    @Min(1)
    private Long expiration;
}
