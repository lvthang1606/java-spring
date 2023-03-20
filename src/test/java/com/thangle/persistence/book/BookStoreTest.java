package com.thangle.persistence.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.thangle.fakes.BookFakes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookStoreTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookStore bookStore;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBookEntities();
        when(bookRepository.findAll()).thenReturn(expected);

        assertEquals(expected.size(), bookStore.findAll().size());

        verify(bookRepository).findAll();
    }

    @Test
    void shouldCreate_OK() {
        final var expected = buildBookEntity();
        when(bookRepository.save(any())).thenReturn(expected);

        final var actual = bookStore.create(buildBook());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldUpdate_OK() {
        final var expected = buildBookEntity();
        when(bookRepository.save(any())).thenReturn(expected);

        final var actual = bookStore.update(buildBook());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldDeleteById_OK() {
        final var book = buildBookEntity();

        bookStore.deleteById(book.getId());

        verify(bookRepository).deleteById(book.getId());
    }
}
