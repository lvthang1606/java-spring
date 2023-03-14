package com.thangle.persistence.book;

import com.thangle.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.collections4.IterableUtils.toList;
import static com.thangle.persistence.book.BookEntityMapper.*;

@Repository
@RequiredArgsConstructor
public class BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return toBooks(toList(bookRepository.findAll()));
    }

    public Optional<Book> findById(final UUID id) {
        return bookRepository.findById(id).map(BookEntityMapper::toBook);
    }

    public Optional<Book> findByTitle(final String title) {
        return bookRepository.findByTitle(title).map(BookEntityMapper::toBook);
    }

    public Book create(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public Book update(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public void deleteById(final UUID id) {
        bookRepository.deleteById(id);
    }
}
