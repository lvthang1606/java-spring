package com.thangle.api.book;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BookRequestDTO {

    private String title;
    private String author;
    private String description;
    private String image;
}
