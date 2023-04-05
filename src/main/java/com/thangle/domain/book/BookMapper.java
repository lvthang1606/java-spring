package com.thangle.domain.book;

import lombok.experimental.UtilityClass;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookMapper {

    public static Book toBook(final Object[] objects) {
        return Book.builder()
                .title((String) objects[1])
                .subtitle((String) objects[2])
                .author((String) objects[3])
                .publisher((String) objects[4])
                .isbn13(parseLong((objects[5].toString())))
                .description((String) objects[6])
                .image((String) objects[9])
                .price(parseDouble(objects[10].toString()))
                .year(integerValue(objects[11].toString()))
                .rating(parseDouble(objects[12].toString()))
                .build();
    }

    public static List<Book> toBooks(final List<Object[]> objects) {
        return emptyIfNull(objects)
                .stream()
                .map(BookMapper::toBook)
                .toList();
    }

    private Integer integerValue(final String string) {
        Number number = null;
        try {
            number = NumberFormat.getInstance().parse(string);
        } catch (ParseException exception) {
            throw new RuntimeException("The string value cannot be parsed to integer value", exception);
        }
        return number.intValue();
    }
}
