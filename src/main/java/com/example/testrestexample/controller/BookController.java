package com.example.testrestexample.controller;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

   private final BookService bookService;

   @GetMapping("/{bookId}")
   ResponseEntity<Book> getBookById(@PathVariable Long bookId){
      return ResponseEntity.of(bookService.getBookById(bookId));
   }
}
