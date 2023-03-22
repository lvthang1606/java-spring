package com.thangle.api.book;

import com.thangle.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.thangle.api.book.BookResponseDTOMapper.*;
import static com.thangle.api.book.BookRequestDTOMapper.*;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Find all available books")
    @GetMapping
    public List<BookResponseDTO> findAll() {
        return toBookResponseDTOs(bookService.findAll());
    }

    @Operation(summary = "Find a specific book by id")
    @GetMapping("{id}")
    public BookResponseDTO findById(final @PathVariable UUID id) {
        return toBookResponseDTO(bookService.findById(id));
    }

    @Operation(summary = "Find books by title, author or description")
    @GetMapping("search")
    public List<BookResponseDTO> find(final @RequestParam String searchTerm) {
        return toBookResponseDTOs(bookService.find(searchTerm));
    }

    @Operation(summary = "Create a specific book")
    @PostMapping
    public BookResponseDTO create(final @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookResponseDTO(bookService.create(toBook(bookRequestDTO)));
    }

    @Operation(summary = "Update a specific book")
    @PutMapping("{id}")
    public BookResponseDTO update(final @PathVariable UUID id, final @RequestBody BookRequestDTO bookRequestDTO) {
        return toBookResponseDTO(bookService.update(id, toBook(bookRequestDTO)));
    }

    @Operation(summary = "Delete a specific book")
    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable UUID id) {
        bookService.deleteById(id);
    }
}
