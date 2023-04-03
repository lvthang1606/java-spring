package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookRequestDTOMapper {

    public static Book toBook(final BookRequestDTO bookRequestDTO) {
        return Book.builder()
                .title(bookRequestDTO.getTitle())
                .subtitle(bookRequestDTO.getSubtitle())
                .author(bookRequestDTO.getAuthor())
                .publisher(bookRequestDTO.getPublisher())
                .isbn13(bookRequestDTO.getIsbn13())
                .description(bookRequestDTO.getDescription())
                .image(bookRequestDTO.getImage())
                .price(bookRequestDTO.getPrice())
                .year(bookRequestDTO.getYear())
                .rating(bookRequestDTO.getRating())
                .userId(bookRequestDTO.getUserId())
                .build();
    }

    public static BookRequestDTO toBookRequestDTO(final Book book) {
        return BookRequestDTO.builder()
                .title(book.getTitle())
                .subtitle(book.getSubtitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn13(book.getIsbn13())
                .description(book.getDescription())
                .image(book.getImage())
                .price(book.getPrice())
                .year(book.getYear())
                .rating(book.getRating())
                .userId(book.getUserId())
                .build();
    }
}
