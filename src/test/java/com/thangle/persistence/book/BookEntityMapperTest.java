package com.thangle.persistence.book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.thangle.fakes.BookFakes.buildBookEntities;
import static com.thangle.fakes.BookFakes.buildBookEntity;
import static com.thangle.persistence.book.BookEntityMapper.toBook;
import static com.thangle.persistence.book.BookEntityMapper.toBooks;

class BookEntityMapperTest {

    @Test
    void shouldToBook_OK() {
        final var bookEntity = buildBookEntity();
        final var book = toBook(bookEntity);

        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getTitle(), book.getTitle());
    }

    @Test
    void shouldToBooks_OK() {
        final var bookEntities = buildBookEntities();
        final var book = toBooks(bookEntities);

        assertEquals(bookEntities.size(), book.size());
    }
}
