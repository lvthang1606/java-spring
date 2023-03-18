package com.thangle.api.user;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID roleId;
}
