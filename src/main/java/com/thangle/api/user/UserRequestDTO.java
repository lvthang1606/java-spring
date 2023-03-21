package com.thangle.api.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserRequestDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private String avatar;
    private UUID roleId;
}
