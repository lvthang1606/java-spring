package com.thangle.persistence.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

@Table(value = "books")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    private UUID id;
    private String title;
    private String author;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String image;
    private UUID userId;
}
