package com.thangle.api.book;

import com.thangle.api.AbstractControllerTest;
import com.thangle.api.WithMockAdmin;
import com.thangle.api.WithMockContributor;
import com.thangle.domain.auth.AuthsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.thangle.fakes.BookFakes.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import com.thangle.domain.book.BookService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.List;

import static com.thangle.api.book.BookRequestDTOMapper.toBookRequestDTO;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/books";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private AuthsProvider authsProvider;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication()).thenCallRealMethod();
    }

    @Test
    @WithMockUser
    void shouldFindAll_OK() throws Exception {
        final var books = buildBooks();

        when(bookService.findAll()).thenReturn(books);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()))
                .andExpect(jsonPath("$[0].id").value(books.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].title").value(books.get(0).getTitle()))
                .andExpect(jsonPath("$[0].subtitle").value(books.get(0).getSubtitle()))
                .andExpect(jsonPath("$[0].author").value(books.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].publisher").value(books.get(0).getPublisher()))
                .andExpect(jsonPath("$[0].isbn13").value(books.get(0).getIsbn13()))
                .andExpect(jsonPath("$[0].description").value(books.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(books.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(books.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(books.get(0).getImage()))
                .andExpect(jsonPath("$[0].price").value(books.get(0).getPrice()))
                .andExpect(jsonPath("$[0].year").value(books.get(0).getYear()))
                .andExpect(jsonPath("$[0].rating").value(books.get(0).getRating()))
                .andExpect(jsonPath("$[0].userId").value(books.get(0).getUserId().toString()));

        verify(bookService).findAll();
    }

    @Test
    @WithMockUser
    void shouldFindById_OK() throws Exception {
        final var book = buildBook();

        when(bookService.findById(book.getId())).thenReturn(book);

        get(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.subtitle").value(book.getSubtitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(book.getIsbn13()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.price").value(book.getPrice()))
                .andExpect(jsonPath("$.year").value(book.getYear()))
                .andExpect(jsonPath("$.rating").value(book.getRating()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));

        verify(bookService).findById(book.getId());
    }

    @Test
    @WithMockUser
    void shouldFindByTitleAuthorDescription_OK() throws Exception {
        final var book = buildBook();

        when(bookService.find(book.getTitle())).thenReturn(List.of(book));

        get(BASE_URL + "/search?searchTerm=" + book.getTitle())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book.getId().toString()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].subtitle").value(book.getSubtitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].publisher").value(book.getPublisher()))
                .andExpect(jsonPath("$[0].isbn13").value(book.getIsbn13()))
                .andExpect(jsonPath("$[0].description").value(book.getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].image").value(book.getImage()))
                .andExpect(jsonPath("$[0].price").value(book.getPrice()))
                .andExpect(jsonPath("$[0].year").value(book.getYear()))
                .andExpect(jsonPath("$[0].rating").value(book.getRating()))

                .andExpect(jsonPath("$[0].userId").value(book.getUserId().toString()));
    }

    @Test
    @WithMockAdmin
    void shouldCreateWithRoleAdmin_OK() throws Exception {
        final var book = buildBook();
        final var bookRequestDTO = toBookRequestDTO(book);

        when(bookService.create(argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(book);

        post(BASE_URL, bookRequestDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.subtitle").value(book.getSubtitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(book.getIsbn13()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.price").value(book.getPrice()))
                .andExpect(jsonPath("$.year").value(book.getYear()))
                .andExpect(jsonPath("$.rating").value(book.getRating()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));
    }

    @Test
    @WithMockContributor
    void shouldCreateWithRoleContributor_OK() throws Exception {
        final var book = buildBook();
        final var bookRequestDTO = toBookRequestDTO(book);

        when(bookService.create(argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(book);

        post(BASE_URL, bookRequestDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.subtitle").value(book.getSubtitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(book.getIsbn13()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()))
                .andExpect(jsonPath("$.price").value(book.getPrice()))
                .andExpect(jsonPath("$.year").value(book.getYear()))
                .andExpect(jsonPath("$.rating").value(book.getRating()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()));
    }

    @Test
    void shouldCreateWithoutRole_ThrowsUnauthorizedException() throws Exception {
        final var book = buildBook();
        final var bookRequestDTO = toBookRequestDTO(book);

        when(bookService.create(argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(book);

        post(BASE_URL, bookRequestDTO)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Unauthorized")));
    }

    @Test
    @WithMockAdmin
    void shouldUpdateWithRoleAdmin_OK() throws Exception {
        final var bookNeedsToBeUpdated = buildBook();
        final var updatedBook = buildBook().withId(bookNeedsToBeUpdated.getId());
        final var bookRequestDTO = toBookRequestDTO(updatedBook);

        when(bookService.update(eq(bookNeedsToBeUpdated.getId()), argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(updatedBook);

        put(BASE_URL + "/" + bookNeedsToBeUpdated.getId(), bookRequestDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBook.getId().toString()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.subtitle").value(updatedBook.getSubtitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.publisher").value(updatedBook.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(updatedBook.getIsbn13()))
                .andExpect(jsonPath("$.description").value(updatedBook.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(updatedBook.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(updatedBook.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(updatedBook.getImage()))
                .andExpect(jsonPath("$.price").value(updatedBook.getPrice()))
                .andExpect(jsonPath("$.year").value(updatedBook.getYear()))
                .andExpect(jsonPath("$.rating").value(updatedBook.getRating()))
                .andExpect(jsonPath("$.userId").value(updatedBook.getUserId().toString()));
    }

    @Test
    @WithMockContributor
    void shouldUpdateWithRoleContributor_OK() throws Exception {
        final var bookNeedsToBeUpdated = buildBook();
        final var updatedBook = buildBook().withId(bookNeedsToBeUpdated.getId());
        final var bookRequestDTO = toBookRequestDTO(updatedBook);

        when(bookService.update(eq(bookNeedsToBeUpdated.getId()), argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(updatedBook);

        put(BASE_URL + "/" + bookNeedsToBeUpdated.getId(), bookRequestDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBook.getId().toString()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.subtitle").value(updatedBook.getSubtitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()))
                .andExpect(jsonPath("$.publisher").value(updatedBook.getPublisher()))
                .andExpect(jsonPath("$.isbn13").value(updatedBook.getIsbn13()))
                .andExpect(jsonPath("$.description").value(updatedBook.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(updatedBook.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(updatedBook.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.image").value(updatedBook.getImage()))
                .andExpect(jsonPath("$.price").value(updatedBook.getPrice()))
                .andExpect(jsonPath("$.year").value(updatedBook.getYear()))
                .andExpect(jsonPath("$.rating").value(updatedBook.getRating()))
                .andExpect(jsonPath("$.userId").value(updatedBook.getUserId().toString()));
    }

    @Test
    void shouldUpdateWithoutRole_ThrowsUnauthorizedException() throws Exception {
        final var bookNeedsToBeUpdated = buildBook();
        final var updatedBook = buildBook().withId(bookNeedsToBeUpdated.getId());
        final var bookRequestDTO = toBookRequestDTO(updatedBook);

        when(bookService.update(eq(bookNeedsToBeUpdated.getId()), argThat(x -> x.getTitle().equals(bookRequestDTO.getTitle()))))
                .thenReturn(updatedBook);

        put(BASE_URL + "/" + bookNeedsToBeUpdated.getId(), bookRequestDTO)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Unauthorized")));
    }

    @Test
    @WithMockAdmin
    void shouldDeleteByIdWithRoleAdmin_OK() throws Exception {
        final var book = buildBook();

        delete(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk());

        verify(bookService).deleteById(book.getId());
    }

    @Test
    @WithMockContributor
    void shouldDeleteByIdWithRoleContributor_OK() throws Exception {
        final var book = buildBook();

        delete(BASE_URL + "/" + book.getId())
                .andExpect(status().isOk());

        verify(bookService).deleteById(book.getId());
    }

    @Test
    void shouldDeleteByIdWithoutRole_ThrowsUnauthorizedException() throws Exception {
        final var book = buildBook();

        delete(BASE_URL + "/" + book.getId())
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Unauthorized")));
    }

    @Test
    @WithMockUser
    void shouldUploadFile_ThrowsBadRequest() throws Exception {
        final File dataFile = resourceLoader.getResource("classpath/import.xlsx").getFile();

        final MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "import.xlsx",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                dataFile.getAbsolutePath().getBytes()
        );

        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart(BASE_URL + "/import").file(file))
                .andExpect(status().isBadRequest());
    }
}
