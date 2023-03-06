package com.thangle.domain.book;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Book {
    private UUID id;
    private String title;
}
