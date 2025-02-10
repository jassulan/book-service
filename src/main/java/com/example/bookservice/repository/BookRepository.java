package com.example.bookservice.repository;

import com.example.bookservice.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CrudRepository
// ListCrudRepository
// ListPagingAndSortingRepository
// PagingAndSortingRepository
// JpaRepository

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
   Optional<Book> findByTitle(String title);
}