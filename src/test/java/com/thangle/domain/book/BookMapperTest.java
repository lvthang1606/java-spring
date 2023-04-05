package com.thangle.domain.book;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thangle.fakes.ObjectFakes.buildObjectList;
import static com.thangle.fakes.ObjectFakes.buildObjects;
import static com.thangle.domain.book.BookMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookMapperTest {

    @Test
    void shouldToBook_OK() {
        final var objects = buildObjects();
        final var book = toBook(objects);

        assertEquals(objects[1], book.getTitle());
        assertEquals(objects[2], book.getSubtitle());
        assertEquals(objects[3], book.getAuthor());
        assertEquals(objects[4], book.getPublisher());
        assertEquals(objects[5], book.getIsbn13());
        assertEquals(objects[6], book.getDescription());
        assertEquals(objects[9], book.getImage());
        assertEquals(objects[10], book.getPrice());
        assertEquals(objects[11], book.getYear());
        assertEquals(objects[12], book.getRating());
    }

    @Test
    void shouldToBooks_OK() {
        final List<Object[]> objectList = buildObjectList();
        final var books = toBooks(objectList);

        assertEquals(objectList.size(), books.size());
    }

    @Test
    void shouldToBook_WithParseStringThrowsRuntimeException() {
        final var objects = buildObjects();

        objects[11] = "";

        assertThrows(RuntimeException.class, () -> toBook(objects));
    }
}
