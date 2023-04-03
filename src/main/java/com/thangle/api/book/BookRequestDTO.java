package com.thangle.api.book;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BookRequestDTO {

    private String title;
    private String subtitle;
    private String author;
    private String publisher;
    private Long isbn13;
    private String description;
    private String image;
    private Double price;
    private Integer year;
    private Double rating;
    private UUID userId;
}
