package com.thangle.persistence.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table(value = "users")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@With
public class UserEntity {

    @Id
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
