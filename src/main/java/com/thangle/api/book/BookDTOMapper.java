package com.thangle.api.book;

import com.thangle.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookDTOMapper {
    public static BookDTO toBookDTO(final Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .build();
    }

    public static List<BookDTO> toBookDTOs(final List<Book> books) {
        return emptyIfNull(books)
                .stream()
                .map(BookDTOMapper::toBookDTO)
                .toList();
    }
}
