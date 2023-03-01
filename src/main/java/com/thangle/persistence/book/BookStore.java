package com.thangle.persistence.book;

import com.thangle.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.apache.commons.collections4.IterableUtils.toList;
import static com.thangle.persistence.book.BookEntityMapper.toBooks;

@Repository
@RequiredArgsConstructor
public class BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        List<Book> books = toBooks(toList(bookRepository.findAll()));
        return books;
    }
}
