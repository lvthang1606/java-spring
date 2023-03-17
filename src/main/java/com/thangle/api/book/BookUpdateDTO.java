package com.thangle.api.book;

import com.thangle.common.UpdateDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BookUpdateDTO implements UpdateDTO {
    private String title;
    private String author;
    private String description;
    private String image;
    private UUID userId;
}
