package com.thangle.domain.book;

import com.thangle.domain.auth.AuthsProvider;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.thangle.persistence.book.BookStore;

import static com.thangle.domain.book.BookError.supplyBookNotFound;
import static com.thangle.error.CommonError.supplyForbiddenError;
import static com.thangle.domain.book.BookValidation.validateBook;
import static com.thangle.domain.book.BookMapper.toBooks;

import java.io.IOException;
import java.io.InputStream;
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
        book.setSubtitle(updatedBook.getSubtitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublisher(updatedBook.getPublisher());
        book.setIsbn13(updatedBook.getIsbn13());
        book.setDescription(updatedBook.getDescription());
        book.setUpdatedAt(Instant.now());
        book.setImage(updatedBook.getImage());
        book.setPrice(updatedBook.getPrice());
        book.setYear(updatedBook.getYear());
        book.setRating(updatedBook.getRating());

        return bookStore.save(book);
    }

    public void deleteById(final UUID id) {
        final Book book = findById(id);

        validateBookActionPermission(book);

        bookStore.deleteById(book.getId());
    }

    public List<Book> saveDataFromExcel(final InputStream inputStream) {
        final List<Object[]> objects;

        try(final XSSFWorkbook workBook = new XSSFWorkbook(inputStream)) {
            final XSSFSheet sheet = workBook.getSheetAt(0);
            objects = ExcelReader.readTable(sheet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final List<Book> books = toBooks(objects);
        books.forEach(book -> {
            book.setCreatedAt(Instant.now());
            book.setUserId(authsProvider.getCurrentUserId());
        });

        return bookStore.saveAll(books);
    }

    private void validateBookActionPermission(final Book book) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyForbiddenError().get();
        }
    }
}
