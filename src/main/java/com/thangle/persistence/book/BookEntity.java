package com.thangle.persistence.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import javax.persistence.Index;
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
    private String subtitle;
    private String author;
    private String publisher;
    private Long isbn13;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String image;
    private Double price;
    private Integer year;
    private Double rating;
    private UUID userId;
}
