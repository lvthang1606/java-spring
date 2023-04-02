package com.thangle.domain.book;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@With
public class Book {

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
