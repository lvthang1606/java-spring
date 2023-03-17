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

    private void verifyTitleAndAuthorAvailable(final Book book, final UUID id, final Activity activity) {
        final List<Book> books = bookStore.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (activity == Activity.UPDATE) {
            //Indicates that the book has the same title and the author is not the one we are updating
            boolean noneMatch = books.stream().noneMatch(bookElement -> bookElement.getId().equals(id));
            if (noneMatch) {
                throw new BadRequestException("The book with title %s and author %s already exists", book.getTitle(), book.getAuthor());
            } else {
                return;
            }
        }
        if (!books.isEmpty()) {
            throw new BadRequestException("The book with title %s and author %s already exists", book.getTitle(), book.getAuthor());
        }
    }

    public Book create(final Book book) {
        validateBook(book);
        verifyTitleAndAuthorAvailable(book, book.getId(),Activity.CREATE);
        book.setCreatedAt(Instant.now());
        return bookStore.create(book);
    }

    public Book update(final UUID id, final Book updatedBook) {
        final Book book = findById(id);
        validateBook(updatedBook);
        verifyTitleAndAuthorAvailable(updatedBook, id, Activity.UPDATE);

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
