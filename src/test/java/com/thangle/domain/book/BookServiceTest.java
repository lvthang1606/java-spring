package com.thangle.domain.book;

import com.thangle.domain.auth.AuthsProvider;
import com.thangle.error.ForbiddenException;
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

import static com.thangle.fakes.UserAuthenticationTokenFakes.*;
import static java.util.UUID.randomUUID;

import java.io.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;

    @Mock
    private AuthsProvider authsProvider;

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
        assertEquals(expected.get(0).getSubtitle(), actual.get(0).getSubtitle());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getPublisher(), actual.get(0).getPublisher());
        assertEquals(expected.get(0).getIsbn13(), actual.get(0).getIsbn13());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());
        assertEquals(expected.get(0).getPrice(), actual.get(0).getPrice());
        assertEquals(expected.get(0).getYear(), actual.get(0).getYear());
        assertEquals(expected.get(0).getRating(), actual.get(0).getRating());
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
    void shouldCreateWithRoleAdmin_OK() {
        final var book = buildBook();
        final var user = buildAdmin();

        when(bookStore.save(book)).thenReturn(book);
        when(authsProvider.getCurrentUserId()).thenReturn(user.getUserId());

        final var updatedBook = bookService.create(book);

        assertEquals(book, updatedBook);

        verify(bookStore).save(book);
        verify(authsProvider).getCurrentUserId();
    }

    @Test
    void shouldCreateWithRoleContributor_OK() {
        final var book = buildBook();
        final var user = buildContributor();

        when(bookStore.save(book)).thenReturn(book);
        when(authsProvider.getCurrentUserId()).thenReturn(user.getUserId());

        final var updatedBook = bookService.create(book);

        assertEquals(book, updatedBook);

        verify(bookStore).save(book);
        verify(authsProvider).getCurrentUserId();
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
    void shouldUpdateWithRoleAdmin_OK() {
        final var book = buildBook();
        final var updatedBook = buildBook().withId(book.getId());

        final var user = buildAdmin();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(updatedBook);

        when(authsProvider.getCurrentUserId()).thenReturn(user.getUserId());
        when(authsProvider.getCurrentUserRole()).thenReturn(user.getRole());

        book.setUserId(authsProvider.getCurrentUserId());
        updatedBook.setUserId(authsProvider.getCurrentUserId());

        final var actual = bookService.update(book.getId(), updatedBook);
        assertEquals(updatedBook.getId(), actual.getId());
        assertEquals(updatedBook.getTitle(), actual.getTitle());
        assertEquals(updatedBook.getSubtitle(), actual.getSubtitle());
        assertEquals(updatedBook.getAuthor(), actual.getAuthor());
        assertEquals(updatedBook.getPublisher(), actual.getPublisher());
        assertEquals(updatedBook.getIsbn13(), actual.getIsbn13());
        assertEquals(updatedBook.getDescription(), actual.getDescription());
        assertEquals(updatedBook.getCreatedAt(), actual.getCreatedAt());
        assertEquals(updatedBook.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(updatedBook.getImage(), actual.getImage());
        assertEquals(updatedBook.getPrice(), actual.getPrice());
        assertEquals(updatedBook.getYear(), actual.getYear());
        assertEquals(updatedBook.getRating(), actual.getRating());
        assertEquals(updatedBook.getUserId(), actual.getUserId());

        verify(bookStore).findById(book.getId());
        verify(bookStore).save(book);

        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldUpdateWithRoleContributor_OK() {
        final var book = buildBook();
        final var updatedBook = buildBook().withId(book.getId());

        final var user = buildContributor();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(updatedBook);

        when(authsProvider.getCurrentUserId()).thenReturn(user.getUserId());
        when(authsProvider.getCurrentUserRole()).thenReturn(user.getRole());

        book.setUserId(authsProvider.getCurrentUserId());
        updatedBook.setUserId(authsProvider.getCurrentUserId());

        final var actual = bookService.update(book.getId(), updatedBook);
        assertEquals(updatedBook.getId(), actual.getId());
        assertEquals(updatedBook.getTitle(), actual.getTitle());
        assertEquals(updatedBook.getSubtitle(), actual.getSubtitle());
        assertEquals(updatedBook.getAuthor(), actual.getAuthor());
        assertEquals(updatedBook.getPublisher(), actual.getPublisher());
        assertEquals(updatedBook.getIsbn13(), actual.getIsbn13());
        assertEquals(updatedBook.getDescription(), actual.getDescription());
        assertEquals(updatedBook.getCreatedAt(), actual.getCreatedAt());
        assertEquals(updatedBook.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(updatedBook.getImage(), actual.getImage());
        assertEquals(updatedBook.getPrice(), actual.getPrice());
        assertEquals(updatedBook.getYear(), actual.getYear());
        assertEquals(updatedBook.getRating(), actual.getRating());
        assertEquals(updatedBook.getUserId(), actual.getUserId());

        verify(bookStore).findById(book.getId());
        verify(bookStore).save(book);

        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldUpdate_ThrowsForbiddenException() {
        final var book = buildBook();
        final var updatedBook = buildBook();

        final var user = buildContributor();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));
        when(authsProvider.getCurrentUserId()).thenReturn(UUID.randomUUID());
        when(authsProvider.getCurrentUserRole()).thenReturn(user.getRole());

        assertThrows(ForbiddenException.class, () -> bookService.update(book.getId(), updatedBook));

        verify(bookStore).findById(book.getId());

        verify(authsProvider).getCurrentUserId();
        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldUpdate_ThrowsNotFound() {
        final var book = buildBook();
        final var updatedBook = buildBook().withId(book.getId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.update(book.getId(), updatedBook));

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdate_WithTitleEmpty() {
        final var book = buildBook();
        final var updatedBook = buildBook()
                                .withId(book.getId())
                                .withTitle("");

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), updatedBook));

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdate_WithAuthorEmpty() {
        final var book = buildBook();
        final var updatedBook = buildBook()
                                .withId(book.getId())
                                .withAuthor("");

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), updatedBook));

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdate_WithUserIdNull() {
        final var book = buildBook();
        final var updatedBook = buildBook()
                                .withId(book.getId())
                                .withUserId(null);

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(updatedBook));

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), updatedBook));

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteByIdWithRoleAdmin_OK() {
        final var book = buildBook();
        final var user = buildAdmin();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId()).thenReturn(user.getUserId());
        when(authsProvider.getCurrentUserRole()).thenReturn(user.getRole());

        book.setUserId(authsProvider.getCurrentUserId());

        bookService.deleteById(book.getId());

        verify(bookStore).deleteById(book.getId());
        verify(bookStore).findById(book.getId());

        verify(authsProvider).getCurrentUserId();
        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldDeleteByIdWithRoleContributor_OK() {
        final var book = buildBook();

        final var user = buildContributor();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId()).thenReturn(user.getUserId());
        when(authsProvider.getCurrentUserRole()).thenReturn(user.getRole());

        book.setUserId(authsProvider.getCurrentUserId());

        bookService.deleteById(book.getId());

        verify(bookStore).deleteById(book.getId());
        verify(bookStore).findById(book.getId());

        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldDeleteById_ThrowsForbiddenException() {
        final var book = buildBook();
        final var user = buildContributor();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId()).thenReturn(UUID.randomUUID());
        when(authsProvider.getCurrentUserRole()).thenReturn(user.getRole());

        assertThrows(ForbiddenException.class, () -> bookService.deleteById(book.getId()));

        verify(bookStore).findById(book.getId());

        verify(authsProvider).getCurrentUserId();
        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldSaveDateFromExcel_OK() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("import.xlsx");

        List<Book> bookList = ImportBookHelper.extractDataFromInputStream(inputStream, randomUUID());

        assertEquals("title", bookList.get(0).getTitle());
        assertEquals("subtitle", bookList.get(0).getSubtitle());
        assertEquals("author", bookList.get(0).getAuthor());
        assertEquals("publisher", bookList.get(0).getPublisher());
        assertEquals("description", bookList.get(0).getDescription());
        assertEquals("image", bookList.get(0).getImage());
        assertEquals("50.0", bookList.get(0).getPrice().toString());
        assertEquals("1998", bookList.get(0).getYear().toString());
        assertEquals("5.0", bookList.get(0).getRating().toString());
    }
}
