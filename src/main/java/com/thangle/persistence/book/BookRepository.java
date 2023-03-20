package com.thangle.persistence.book;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query(value = "SELECT * FROM books WHERE title ILIKE CONCAT('%', :searchTerm, '%') " +
            "OR author ILIKE CONCAT('%', :searchTerm, '%') " +
            "OR description ILIKE CONCAT('%', :searchTerm, '%') ")
    List<BookEntity> find(final String searchTerm);
}
