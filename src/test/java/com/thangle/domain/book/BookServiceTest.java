package com.thangle.domain.book;

import com.thangle.error.BadRequestException;
import com.thangle.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.thangle.fakes.BookFakes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thangle.persistence.book.BookStore;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBooks();

        when(bookStore.findAll()).thenReturn(expected);

        final var actual = bookService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());
        assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());

        verify(bookStore).findAll();
    }

    @Test
    void shouldFindByTitleAuthorDescription_OK() throws Exception {
        final var book = buildBook();
        final var expected = buildBooks();

        when(bookStore.find(book.getTitle())).thenReturn(expected);

        final var actual = bookService.find(book.getTitle());

        assertEquals(expected, actual);

        verify(bookStore).find(book.getTitle());
    }

    @Test
    void shouldCreate_OK() {
        final var book = buildBook();
        when(bookStore.save(book)).thenReturn(book);

        final var updatedBook = bookService.create(book);

        assertEquals(book, updatedBook);

        verify(bookStore).save(book);
    }

    @Test
    void shouldCreate_WithTitleEmpty() {
        final var book = buildBook().withTitle("");

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldCreate_WithAuthorEmpty() {
        final var book = buildBook().withAuthor("");

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldCreate_WithUserIdNull() {
        final var book = buildBook().withUserId(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldUpdate_OK() {
        final var book = buildBook();
        final var updatedBook = buildBook().withId(book.getId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(updatedBook);

        final var actual = bookService.update(book.getId(), updatedBook);
        assertEquals(updatedBook.getId(), actual.getId());
        assertEquals(updatedBook.getTitle(), actual.getTitle());
        assertEquals(updatedBook.getAuthor(), actual.getAuthor());
        assertEquals(updatedBook.getDescription(), actual.getDescription());
        assertEquals(updatedBook.getCreatedAt(), actual.getCreatedAt());
        assertEquals(updatedBook.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(updatedBook.getImage(), actual.getImage());
        assertEquals(updatedBook.getUserId(), actual.getUserId());
    }

    @Test
    void shouldUpdate_ThrowsNotFound() {
        final var book = buildBook();
        final var updatedBook = buildBook().withId(book.getId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.update(book.getId(), updatedBook));
    }

    @Test
    void shouldUpdate_WithTitleEmpty() {
        final var book = buildBook();
        final var updatedBook = buildBook()
                                .withId(book.getId())
                                .withTitle("");

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), updatedBook));
    }

    @Test
    void shouldUpdate_WithAuthorEmpty() {
        final var book = buildBook();
        final var updatedBook = buildBook()
                                .withId(book.getId())
                                .withAuthor("");

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), updatedBook));
    }

    @Test
    void shouldUpdate_WithUserIdNull() {
        final var book = buildBook();
        final var updatedBook = buildBook()
                                .withId(book.getId())
                                .withUserId(null);

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), updatedBook));
    }

    @Test
    void shouldDeleteById_OK() {
        final var book = buildBook();
        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.deleteById(book.getId());

        verify(bookStore).deleteById(book.getId());
    }
}
