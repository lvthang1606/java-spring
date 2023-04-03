package com.thangle.api.book;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class BookResponseDTO {

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
