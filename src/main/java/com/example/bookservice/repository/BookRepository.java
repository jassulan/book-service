package com.example.bookservice.repository;

import com.example.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CrudRepository
// ListCrudRepository
// ListPagingAndSortingRepository
// PagingAndSortingRepository
// JpaRepository

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}