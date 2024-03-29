package com.thangle.persistence.book;

import org.junit.jupiter.api.Test;

import static com.thangle.fakes.BookFakes.*;
import static com.thangle.persistence.book.BookEntityMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookEntityMapperTest {

    @Test
    void shouldToBook_OK() {
        final var bookEntity = buildBookEntity();
        final var book = toBook(bookEntity);

        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getTitle(), book.getTitle());
        assertEquals(bookEntity.getAuthor(), book.getAuthor());
        assertEquals(bookEntity.getDescription(), book.getDescription());
        assertEquals(bookEntity.getCreatedAt(), book.getCreatedAt());
        assertEquals(bookEntity.getUpdatedAt(), book.getUpdatedAt());
        assertEquals(bookEntity.getImage(), book.getImage());
        assertEquals(bookEntity.getUserId(), book.getUserId());
    }

    @Test
    void shouldToBooks_OK() {
        final var bookEntities = buildBookEntities();
        final var book = toBooks(bookEntities);

        assertEquals(bookEntities.size(), book.size());
    }

    @Test
    void shouldToBookEntity_OK() {
        final var book = buildBook();
        final var bookEntity = toBookEntity(book);

        assertEquals(book.getId(), bookEntity.getId());
        assertEquals(book.getTitle(), bookEntity.getTitle());
        assertEquals(book.getAuthor(), bookEntity.getAuthor());
        assertEquals(book.getDescription(), bookEntity.getDescription());
        assertEquals(book.getCreatedAt(), bookEntity.getCreatedAt());
        assertEquals(book.getUpdatedAt(), bookEntity.getUpdatedAt());
        assertEquals(book.getImage(), bookEntity.getImage());
        assertEquals(book.getUserId(), bookEntity.getUserId());
    }
}
