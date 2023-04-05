package com.thangle.fakes;

import com.thangle.persistence.book.BookEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class ObjectFakes {

    public static Object[] buildObjects() {
        final Object[] objects = new Object[14];
        final var random = new Random();

        objects[0] = randomUUID();
        objects[1] = randomAlphabetic(3, 10);
        objects[2] = randomAlphabetic(3, 10);
        objects[3] = randomAlphabetic(3, 10);
        objects[4] = randomAlphabetic(3, 10);
        objects[5] = random.nextLong();
        objects[6] = randomAlphabetic(3, 10);
        objects[7] = random.nextInt();
        objects[8] = random.nextInt();
        objects[9] = randomAlphabetic(3, 10);
        objects[10] = random.nextDouble();
        objects[11] = random.nextInt();
        objects[12] = random.nextDouble();
        objects[13] = randomAlphabetic(3, 10);

        return objects;
    }

    public static List<Object[]> buildObjectList() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildObjects())
                .toList();
    }
}
