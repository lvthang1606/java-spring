package com.thangle.persistence.role;

import lombok.*;

import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Getter
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@With
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
}
