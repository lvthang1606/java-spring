package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookRequestDTOMapper {

    public static Book toBook(final BookRequestDTO bookRequestDTO) {
        return Book.builder()
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .description(bookRequestDTO.getDescription())
                .image(bookRequestDTO.getImage())
                .build();
    }

    public static BookRequestDTO toBookRequestDTO(final Book book) {
        return BookRequestDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .image(book.getImage())
                .build();
    }
}
