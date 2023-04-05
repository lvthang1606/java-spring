package com.thangle.fakes;

import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import com.thangle.domain.book.Book;
import com.thangle.persistence.book.BookEntity;

@UtilityClass
public class BookFakes {

    public static Book buildBook() {
        final var random = new Random();
        return Book.builder()
                .id(UUID.randomUUID())
                .title(randomAlphabetic(3, 10))
                .subtitle(randomAlphabetic(3, 10))
                .author(randomAlphabetic(3, 10))
                .publisher(randomAlphabetic(3, 10))
                .isbn13(random.nextLong())
                .description(randomAlphabetic(3, 10))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .image(randomAlphabetic(3, 10))
                .price(random.nextDouble())
                .year(random.nextInt())
                .rating(random.nextDouble())
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<Book> buildBooks() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBook())
                .toList();
    }

    public static BookEntity buildBookEntity() {
        final var random = new Random();
        return BookEntity.builder()
                .id(UUID.randomUUID())
                .title(randomAlphabetic(3, 10))
                .subtitle(randomAlphabetic(3, 10))
                .author(randomAlphabetic(3, 10))
                .publisher(randomAlphabetic(3, 10))
                .isbn13(random.nextLong())
                .description(randomAlphabetic(3, 10))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .image(randomAlphabetic(3, 10))
                .price(random.nextDouble())
                .year(random.nextInt())
                .rating(random.nextDouble())
                .userId(UUID.randomUUID())
                .build();
    }

    public static List<BookEntity> buildBookEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBookEntity())
                .toList();
    }
}
