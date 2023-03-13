package com.thangle.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
public class User {
    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID roleId;
}
