package com.thangle.persistence.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books", indexes = @Index(name = "isbn_index", columnList = "isbn13", unique = true))
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(generator = "UUID")
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
