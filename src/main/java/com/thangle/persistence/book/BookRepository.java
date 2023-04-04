package com.thangle.persistence.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    @Query(value = "SELECT * FROM books WHERE title ILIKE CONCAT('%', :searchTerm, '%') " +
            "OR author ILIKE CONCAT('%', :searchTerm, '%') " +
            "OR description ILIKE CONCAT('%', :searchTerm, '%') ", nativeQuery = true)
    List<BookEntity> find(final String searchTerm);
}
