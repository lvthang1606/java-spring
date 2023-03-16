package com.thangle.domain.book;

import com.thangle.error.BadRequestException;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.StringUtils.isBlank;

@UtilityClass
public class BookValidation {
    public static void validateBook(final Book book) {
        if (isBlank(book.getTitle())) {
            throw new BadRequestException("Book's title cannot be empty");
        }

        if (isBlank(book.getAuthor())) {
            throw new BadRequestException("Book's author cannot be empty");
        }

        if (book.getUserId() == null) {
            throw new BadRequestException("User cannot be empty");
        }
    }
}
