package com.example.testrestexample.service;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

   private final BookRepository bookRepository;

   public Optional<Book> getBookById(Long bookId){
      return bookRepository.findById(bookId);
   }
}
