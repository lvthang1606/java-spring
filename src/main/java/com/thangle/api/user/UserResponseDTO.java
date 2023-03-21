package com.thangle.api.user;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String avatar;
    private UUID roleId;
}
