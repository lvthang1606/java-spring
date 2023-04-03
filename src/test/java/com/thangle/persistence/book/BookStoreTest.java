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

import static com.thangle.persistence.book.BookEntityMapper.toBooks;

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

        final var actual = bookStore.save(buildBook());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getSubtitle(), actual.getSubtitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getIsbn13(), actual.getIsbn13());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldUpdate_OK() {
        final var expected = buildBookEntity();
        when(bookRepository.save(any())).thenReturn(expected);

        final var actual = bookStore.save(buildBook());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getSubtitle(), actual.getSubtitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getIsbn13(), actual.getIsbn13());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void shouldDeleteById_OK() {
        final var book = buildBookEntity();

        bookStore.deleteById(book.getId());

        verify(bookRepository).deleteById(book.getId());
    }

    @Test
    void shouldSaveAll_OK() {
        final var expected = buildBookEntities();

        when(bookRepository.saveAll(any())).thenReturn(expected);

        final var actual = bookStore.saveAll(toBooks(expected));

        assertEquals(expected.get(0).getId(), actual.get(0).getId());

        verify(bookRepository).saveAll(any());
    }
}
