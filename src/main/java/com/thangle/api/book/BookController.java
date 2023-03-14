package com.thangle.api.book;

import com.thangle.domain.book.Book;
import com.thangle.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.thangle.api.book.BookDTOMapper.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Find all available books")
    @GetMapping
    public List<BookDTO> findAll() {
        return toBookDTOs(bookService.findAll());
    }

    @Operation(summary = "Find a specific book by id")
    @GetMapping("{id}")
    public BookDTO findById(final @PathVariable UUID id) {
        return toBookDTO(bookService.findById(id));
    }

    @Operation(summary = "Create a specific book")
    @PostMapping
    public BookDTO create(final @RequestBody Book book) {
        return toBookDTO(bookService.create(book));
    }

    @Operation(summary = "Update a specific book")
    @PutMapping("{id}")
    public BookDTO update(final @PathVariable UUID id, final @RequestBody BookDTO bookDTO) {
        return toBookDTO(bookService.update(id, toBook(bookDTO)));
    }

    @Operation(summary = "Delete a specific book")
    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable UUID id) {
        bookService.deleteById(id);
    }
}
