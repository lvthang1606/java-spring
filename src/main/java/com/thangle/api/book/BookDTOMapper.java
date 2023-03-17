package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookDTOMapper {
    public static BookResponseDTO toBookDTO(final Book book) {
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

    public static Book toBook(final BookResponseDTO bookResponseDTO) {
        return Book.builder()
                .title(bookResponseDTO.getTitle())
                .author(bookResponseDTO.getAuthor())
                .description(bookResponseDTO.getDescription())
                .image(bookResponseDTO.getImage())
                .userId(bookResponseDTO.getUserId())
                .build();
    }

    public static List<BookResponseDTO> toBookDTOs(final List<Book> books) {
        return emptyIfNull(books)
                .stream()
                .map(BookDTOMapper::toBookDTO)
                .toList();
    }
}
