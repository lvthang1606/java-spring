package com.thangle.domain.book;

import com.thangle.error.BadRequestException;
import com.thangle.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.thangle.fakes.BookFakes.buildBook;
import static com.thangle.fakes.BookFakes.buildBooks;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        when(bookService.find(book.getTitle())).thenReturn(expected);

        final var actual = bookService.find(book.getTitle());

        assertEquals(expected.size(), actual.size());

        verify(bookStore).find(book.getTitle());
    }

    @Test
    void shouldCreate_OK() {
        final var book = buildBook();
        when(bookStore.create(book)).thenReturn(book);

        final var updatedBook = bookService.create(book);

        assertEquals(book, updatedBook);

        verify(bookStore).create(book);
    }

    @Test
    void shouldCreate_ThrowsBadRequestException() {
        final var book = buildBook();
        final var books = Collections.singletonList(book);
        when(bookStore.findByTitleAndAuthor(book.getTitle(), book.getAuthor()))
                .thenReturn(books);

        final var exception = assertThrows(BadRequestException.class, () -> {
            bookService.create(book);
        });

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldUpdate_OK() {
        final var book = buildBook();
        final var updatedBook = buildBook();
        updatedBook.setId(book.getId());
        updatedBook.setUserId(book.getUserId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.update(book)).thenReturn(updatedBook);

        final var expected = bookService.update(book.getId(), updatedBook);
        assertEquals(expected.getId(), updatedBook.getId());
        assertEquals(expected.getTitle(), updatedBook.getTitle());
        assertEquals(expected.getAuthor(), updatedBook.getAuthor());
        assertEquals(expected.getDescription(), updatedBook.getDescription());
        assertEquals(expected.getCreatedAt(), updatedBook.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), updatedBook.getUpdatedAt());
        assertEquals(expected.getImage(), updatedBook.getImage());
        assertEquals(expected.getUserId(), updatedBook.getUserId());
    }

    @Test
    void shouldUpdate_ThrowsNotFoundException() {
        final var bookToUpdate = buildBook();
        when(bookStore.findById(bookToUpdate.getId())).thenThrow(NotFoundException.class);

        final var exception = assertThrows(NotFoundException.class, () -> {
            bookService.update(bookToUpdate.getId(), bookToUpdate);
        });

        assertThrows(NotFoundException.class, () -> bookService.update(bookToUpdate.getId(), bookToUpdate));
    }

    @Test
    void shouldUpdate_ThrowsBadRequestException() {
        final var book = buildBook();
        final var updatedBook = buildBook();
        final var books = Collections.singletonList(book);

        when(bookStore.findById(updatedBook.getId())).thenReturn(Optional.of(updatedBook));
        when(bookStore.findByTitleAndAuthor(updatedBook.getTitle(), updatedBook.getAuthor()))
                .thenReturn(books);

        assertThrows(BadRequestException.class, () -> bookService.update(updatedBook.getId(), updatedBook));
    }

    @Test
    void shouldDeleteById_OK() {
        final var book = buildBook();
        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.deleteById(book.getId());

        verify(bookStore).deleteById(book.getId());
    }
}
