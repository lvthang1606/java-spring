package com.thangle.api.book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.thangle.api.book.BookResponseDTOMapper.toBookResponseDTO;
import static com.thangle.api.book.BookResponseDTOMapper.toBookResponseDTOs;
import static com.thangle.fakes.BookFakes.buildBook;
import static com.thangle.fakes.BookFakes.buildBooks;

public class BookResponseDTOMapperTest {

    @Test
    void shouldToBookDTO_OK() {
        final var book = buildBook();
        final var bookDTO = toBookResponseDTO(book);

        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getAuthor(), bookDTO.getAuthor());
        assertEquals(book.getDescription(), bookDTO.getDescription());
        assertEquals(book.getCreatedAt(), bookDTO.getCreatedAt());
        assertEquals(book.getUpdatedAt(), bookDTO.getUpdatedAt());
        assertEquals(book.getImage(), bookDTO.getImage());
        assertEquals(book.getUserId(), bookDTO.getUserId());
    }

    @Test
    void shouldToBookDTOs_OK() {
        final var books = buildBooks();
        final var bookDTOs = toBookResponseDTOs(books);
        
        assertEquals(books.size(), bookDTOs.size());
    }
}
