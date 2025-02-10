package com.example.testrestexample.repository;

import com.example.testrestexample.model.Book;
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