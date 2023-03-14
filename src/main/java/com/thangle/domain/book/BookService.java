package com.thangle.domain.book;

import com.thangle.error.BadRequestException;
import com.thangle.persistence.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.thangle.domain.book.BookError.supplyBookNotFound;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    public void verifyBookTitleAvailable(final String title) {
        final Optional<Book> optionalBook = bookStore.findByTitle(title);
        if (optionalBook.isPresent()) {
            throw new BadRequestException("The book %s already exists", optionalBook.get().getTitle());
        }
    }

    public Book create(final Book book) {
        verifyInputData(book);
        verifyBookTitleAvailable(book.getTitle());
        return bookStore.create(book);
    }

    private void verifyInputData(final Book book) {
        if (isBlank(book.getTitle())) {
            throw new BadRequestException("Book's title cannot be empty");
        }

        if (isBlank(book.getAuthor())) {
            throw new BadRequestException("Book's author cannot be empty");
        }

        if (isBlank(String.valueOf(book.getUserId()))) {
            throw new BadRequestException("User cannot be empty");
        }
    }

    public Book update(final UUID id, final Book updatedBook) {
        verifyInputData(updatedBook);
        verifyBookTitleAvailable(updatedBook.getTitle());

        final Book book = findById(id);

        if (isBlank(String.valueOf(updatedBook.getId()))) {
            throw new BadRequestException("Id cannot be empty");
        }

        book.setId(updatedBook.getId());
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
