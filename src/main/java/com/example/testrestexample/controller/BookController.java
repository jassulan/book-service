package com.example.testrestexample.controller;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController{
   private final BookService bookService;

   @GetMapping("/{bookId}")
   public ResponseEntity<Book> getBook(@PathVariable Long bookId){
      return ResponseEntity.of(bookService.getBookById(bookId));
   }

   @PostMapping("")
   public ResponseEntity<Book> createBook(@RequestBody Book book){
      return ResponseEntity.ok(bookService.createBook(book));
   }

   @PutMapping("/{bookId}")
   public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book){
      return bookService.updateBook(bookId,book)
            .map(ResponseEntity::ok)
            .orElseGet(()->ResponseEntity.notFound().build());
   }

   @PatchMapping("/{bookId}")
   public ResponseEntity<Book> partialUpdateBook(@PathVariable Long bookId, @RequestBody Book book){
      return bookService.partialUpdateBook(bookId,book)
            .map(ResponseEntity::ok)
            .orElseGet(()->ResponseEntity.notFound().build());
   }

   @DeleteMapping("/{bookId}")
   public ResponseEntity<String> deleteBook(@PathVariable Long bookId)
   {
      if(bookService.deleteBookById(bookId))
         return ResponseEntity.noContent().build();
      return ResponseEntity.notFound().build();
   }
}