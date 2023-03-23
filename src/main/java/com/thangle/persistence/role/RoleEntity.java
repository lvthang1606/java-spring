package com.thangle.persistence.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Table(value = "roles")
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoleEntity {

    @Id
    private UUID id;
    private String name;
}
