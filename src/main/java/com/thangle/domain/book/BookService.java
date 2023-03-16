package com.thangle.domain.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.thangle.error.BadRequestException;
import com.thangle.persistence.book.BookStore;
import static com.thangle.domain.book.BookError.supplyBookNotFound;
import static com.thangle.domain.book.BookValidation.validateBook;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

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

    private void verifyTitleAndAuthorAvailable(final Book book) {
        final List<Book> books = bookStore.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (!books.isEmpty()) {
            throw new BadRequestException("The book with title %s and author %s already exists", book.getTitle(), book.getAuthor());
        }
    }

    public Book create(final Book book) {
        validateBook(book);
        verifyTitleAndAuthorAvailable(book);
        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID id, final Book updatedBook) {
        validateBook(updatedBook);
        verifyTitleAndAuthorAvailable(updatedBook);

        final Book book = findById(id);

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
