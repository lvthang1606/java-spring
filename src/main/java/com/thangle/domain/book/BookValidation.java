package com.thangle.domain.book;

import com.thangle.error.BadRequestException;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@UtilityClass
public class BookValidation {
    public static void validateBook(final Book book) {
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
        validateUserId(book.getUserId());
    }

    private void validateTitle(final String title) {
        if (isBlank(title)) {
            throw new BadRequestException("Book's title cannot be empty");
        }
    }

    private void validateAuthor(final String author) {
        if (isBlank(author)) {
            throw new BadRequestException("Book's author cannot be empty");
        }
    }

    private void validateUserId(final UUID id) {
        if (id == null) {
            throw new BadRequestException("User cannot be empty");
        }
    }
}
