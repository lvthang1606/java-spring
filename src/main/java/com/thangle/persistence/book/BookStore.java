package com.thangle.persistence.book;

import com.thangle.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
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

    public List<Book> find(final String searchTerm) {
        return toBooks(bookRepository.find(searchTerm));
    }

    public Book save(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public List<Book> saveAll(final List<Book> books) {
        return toBooks(IterableUtils.toList(bookRepository.saveAll(toBookEntities(books))));
    }

    public void deleteById(final UUID id) {
        bookRepository.deleteById(id);
    }
}
