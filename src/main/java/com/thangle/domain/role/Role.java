package com.thangle.domain.role;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.util.UUID;

@Builder
@Getter
@With
public class Role {

    private UUID id;
    private String name;
}
