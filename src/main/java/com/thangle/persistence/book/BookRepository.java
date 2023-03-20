package com.thangle.persistence.book;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query(value = "SELECT DISTINCT * FROM books WHERE LOWER(title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<BookEntity> find(final String searchTerm);
}
