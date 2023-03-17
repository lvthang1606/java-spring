package com.thangle.api.book;

import com.thangle.common.ResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class BookResponseDTO implements ResponseDTO {
    private UUID id;
    private String title;
    private String author;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String image;
    private UUID userId;
}
