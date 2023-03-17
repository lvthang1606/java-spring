package com.thangle.api.book;

import com.thangle.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.thangle.api.book.BookDTOMapper.*;
import static com.thangle.api.book.BookChangeDTOMapper.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Find all available books")
    @GetMapping
    public List<BookResponseDTO> findAll() {
        return toBookDTOs(bookService.findAll());
    }

    @Operation(summary = "Find a specific book by id")
    @GetMapping("{id}")
    public BookResponseDTO findById(final @PathVariable UUID id) {
        return toBookDTO(bookService.findById(id));
    }

    @Operation(summary = "Find books by title, author or description")
    @GetMapping("search")
    public List<BookResponseDTO> find(final @RequestParam String searchTerm) {
        return toBookDTOs(bookService.find(searchTerm));
    }

    @Operation(summary = "Create a specific book")
    @PostMapping
    public BookChangeDTO create(final @RequestBody BookChangeDTO bookChangeDTO) {
        return toBookChangeDTO(bookService.create(toBook(bookChangeDTO)));
    }

    @Operation(summary = "Update a specific book")
    @PutMapping("{id}")
    public BookResponseDTO update(final @PathVariable UUID id, final @RequestBody BookChangeDTO bookChangeDTO) {
        return toBookDTO(bookService.update(id, toBook(bookChangeDTO)));
    }

    @Operation(summary = "Delete a specific book")
    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable UUID id) {
        bookService.deleteById(id);
    }
}
