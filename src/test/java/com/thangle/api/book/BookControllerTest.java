package com.thangle.api.book;

import com.thangle.api.AbstractControllerTest;
import com.thangle.domain.book.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.thangle.fakes.BookFakes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.thangle.domain.book.BookService;

import java.util.Collections;

import static com.thangle.api.book.BookResponseDTOMapper.toBookResponseDTO;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/books";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void shouldFindAll_OK() throws Exception {
        final var books = buildBooks();

        when(bookService.findAll()).thenReturn(books);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()))
                .andExpect(jsonPath("$[0].id").value(books.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].title").value(books.get(0).getTitle()))
                .andExpect(jsonPath("$[0].author").value(books.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].description").value(books.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(books.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(books.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(books.get(0).getImage()))
                .andExpect(jsonPath("$[0].userId").value(books.get(0).getUserId().toString()));

        verify(bookService).findAll();
    }

    @Test
    void shouldFindById_OK() throws Exception {
        final var book = buildBook();

        when(bookService.findById(book.getId())).thenReturn(book);

        get(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));

        verify(bookService).findById(book.getId());
    }

    @Test
    void shouldFindByTitleAuthorDescription_OK() throws Exception {
        final var book = buildBook();

        when(bookService.find(book.getTitle())).thenReturn(Collections.singletonList(book));

        get(BASE_URL + "/search?searchTerm=" + book.getTitle())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book.getId().toString()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].description").value(book.getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(book.getImage()))
                .andExpect(jsonPath("$[0].userId").value(book.getUserId().toString()));
    }

    @Test
    void shouldCreate_OK() throws Exception {
        final var book = buildBook();

        when(bookService.create(any())).thenReturn(book);

        post(BASE_URL, toBookResponseDTO(book))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));
    }

    @Test
    void shouldUpdate_OK() throws Exception {
        final var bookNeedsToBeUpdated = buildBook();
        final var updatedBook = buildUpdatedBook(bookNeedsToBeUpdated.getId());

        updatedBook.setId(bookNeedsToBeUpdated.getId());

        when(bookService.update(eq(bookNeedsToBeUpdated.getId()), any(Book.class))).thenReturn(updatedBook);

        put(BASE_URL + "/" + bookNeedsToBeUpdated.getId(), updatedBook)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBook.getId().toString()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.description").value(updatedBook.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(updatedBook.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(updatedBook.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(updatedBook.getImage()))
                .andExpect(jsonPath("$.userId").value(updatedBook.getUserId().toString()));
    }

    @Test
    void shouldDeleteById_OK() throws Exception {
        final var book = buildBook();

        delete(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk());

        verify(bookService).deleteById(book.getId());
    }
}
