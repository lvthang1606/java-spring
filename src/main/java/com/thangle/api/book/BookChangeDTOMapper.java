package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookChangeDTOMapper {
    public static Book toBook(final BookChangeDTO bookChangeDTO) {
        return Book.builder()
                .title(bookChangeDTO.getTitle())
                .author(bookChangeDTO.getAuthor())
                .description(bookChangeDTO.getDescription())
                .image(bookChangeDTO.getImage())
                .userId(bookChangeDTO.getUserId())
                .build();
    }

    public static BookChangeDTO toBookChangeDTO(final Book book) {
        return BookChangeDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .image(book.getImage())
                .userId(book.getUserId())
                .build();
    }
}
