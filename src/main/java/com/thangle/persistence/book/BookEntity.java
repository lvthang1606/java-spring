package com.thangle.persistence.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;
import java.util.UUID;

@Table(value = "books")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    public static final String ID_FIELD = "id";

    @Id
    private UUID id;
    private String name;
}
