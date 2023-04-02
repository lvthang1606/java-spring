package com.thangle.domain.book;

import com.thangle.domain.auth.AuthsProvider;
import com.thangle.error.BadRequestException;
import com.thangle.error.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.thangle.persistence.book.BookStore;
import org.springframework.web.multipart.MultipartFile;

import static com.thangle.domain.book.BookError.supplyBookNotFound;
import static com.thangle.error.CommonError.supplyForbiddenError;
import static com.thangle.domain.book.BookValidation.validateBook;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;
    private final AuthsProvider authsProvider;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID id) {
        return bookStore.findById(id).orElseThrow(supplyBookNotFound(id));
    }

    public List<Book> find(final String searchTerm) {
        return bookStore.find(searchTerm);
    }

    public Book create(final Book book) {
        validateBook(book);
        book.setUserId(authsProvider.getCurrentUserId());
        book.setCreatedAt(Instant.now());
        return bookStore.save(book);
    }

    public Book update(final UUID id, final Book updatedBook) {
        final Book book = findById(id);
        validateBook(updatedBook);

        validateBookActionPermission(book);

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setDescription(updatedBook.getDescription());
        book.setUpdatedAt(Instant.now());
        book.setImage(updatedBook.getImage());
        book.setUserId(updatedBook.getUserId());

        return bookStore.save(book);
    }

    public void deleteById(final UUID id) {
        final Book book = findById(id);

        validateBookActionPermission(book);

        bookStore.deleteById(book.getId());
    }

    public List<Book> saveDataFromExcel(final MultipartFile file) {
        final var currentUserId = authsProvider.getCurrentUserId();

        if (ImportBookHelper.hasExcelFormat(file)) {
            try {
                List<Book> books = ImportBookHelper.extractDataFromInputStream(file.getInputStream(), currentUserId);
                return bookStore.saveAll(books);
            } catch (IOException exception) {
                throw new ForbiddenException("Fails to access excel data");
            }
        } else {
            throw new BadRequestException("The format of file %s is not accepted", file.getOriginalFilename());
        }
    }

    private void validateBookActionPermission(final Book book) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyForbiddenError().get();
        }
    }
}
