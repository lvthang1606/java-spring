package com.thangle.persistence.book;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {
    @Query(value = "SELECT * FROM books WHERE title LIKE CONCAT('%', :searchTerm, '%')" +
            "OR author LIKE CONCAT('%', :searchTerm, '%')" +
            "OR description LIKE CONCAT('%', :searchTerm, '%')")
    List<BookEntity> find(final String searchTerm);

    @Query("SELECT * FROM books b WHERE b.title = :title AND b.author = :author")
    List<BookEntity> findByTitleAndAuthor(final String title, final String author);
}
