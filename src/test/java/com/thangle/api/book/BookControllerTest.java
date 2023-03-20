package com.thangle.api.book;

import com.thangle.api.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.thangle.fakes.BookFakes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.thangle.domain.book.BookService;

import java.util.List;

import static com.thangle.api.book.BookRequestDTOMapper.toBookRequestDTO;

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

        when(bookService.find(book.getTitle())).thenReturn(List.of(book));

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
        final var bookRequestDTO = toBookRequestDTO(book);

        when(bookService.create(argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(book);

        post(BASE_URL, bookRequestDTO)
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
        final var updatedBook = buildBook().withId(bookNeedsToBeUpdated.getId());
        final var bookRequestDTO = toBookRequestDTO(updatedBook);

        when(bookService.update(eq(bookNeedsToBeUpdated.getId()), argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(updatedBook);

        put(BASE_URL + "/" + bookNeedsToBeUpdated.getId(), bookRequestDTO)
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
