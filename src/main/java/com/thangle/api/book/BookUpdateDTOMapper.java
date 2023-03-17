package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookUpdateDTOMapper {
    public static Book toBook(final BookUpdateDTO bookUpdateDTO) {
        return Book.builder()
                .id(bookUpdateDTO.getId())
                .title(bookUpdateDTO.getTitle())
                .author(bookUpdateDTO.getAuthor())
                .description(bookUpdateDTO.getDescription())
                .image(bookUpdateDTO.getImage())
                .userId(bookUpdateDTO.getUserId())
                .build();
    }

    public static BookUpdateDTO toBookUpdateDTO(final Book book) {
        return BookUpdateDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .image(book.getImage())
                .userId(book.getUserId())
                .build();
    }
}
