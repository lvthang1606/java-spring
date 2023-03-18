package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookResponseDTOMapper {

    public static BookResponseDTO toBookResponseDTO(final Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .image(book.getImage())
                .userId(book.getUserId())
                .build();
    }

    public static List<BookResponseDTO> toBookResponseDTOs(final List<Book> books) {
        return emptyIfNull(books)
                .stream()
                .map(BookResponseDTOMapper::toBookResponseDTO)
                .toList();
    }
}
