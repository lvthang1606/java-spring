package com.thangle.fakes;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import com.thangle.domain.book.Book;
import com.thangle.persistence.book.BookEntity;

@UtilityClass
public class BookFakes {

    public static Book buildBook() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title(RandomStringUtils.randomAlphabetic(3, 10))
                .author(RandomStringUtils.randomAlphabetic(3, 10))
                .description(RandomStringUtils.randomAlphabetic(3, 10))
                .createdAt(Instant.EPOCH)
                .updatedAt(Instant.EPOCH)
                .image(RandomStringUtils.randomAlphabetic(3, 10))
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<Book> buildBooks() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBook())
                .toList();
    }

    public static BookEntity buildBookEntity() {
        return BookEntity.builder()
                .id(UUID.randomUUID())
                .title(RandomStringUtils.randomAlphabetic(3, 10))
                .author(RandomStringUtils.randomAlphabetic(3, 10))
                .description(RandomStringUtils.randomAlphabetic(3, 10))
                .createdAt(Instant.EPOCH)
                .updatedAt(Instant.EPOCH)
                .image(RandomStringUtils.randomAlphabetic(3, 10))
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<BookEntity> buildBookEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBookEntity())
                .toList();
    }
}
