package com.thangle.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.thangle.error.BadRequestException;
import com.thangle.persistence.book.BookStore;
import org.apache.commons.lang3.ArrayUtils;

import static com.thangle.domain.book.BookError.supplyBookNotFound;
import static com.thangle.domain.book.BookValidation.validateBook;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    enum Activity {
        CREATE,
        UPDATE
    }

    private final BookStore bookStore;

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
        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID id, final Book updatedBook) {
        final Book book = findById(id);
        validateBook(updatedBook);

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setDescription(updatedBook.getDescription());
        book.setUpdatedAt(Instant.now());
        book.setImage(updatedBook.getImage());
        book.setUserId(updatedBook.getUserId());

        return bookStore.update(book);
    }

    public void deleteById(final UUID id) {
        final Book book = findById(id);
        bookStore.deleteById(book.getId());
    }
}
