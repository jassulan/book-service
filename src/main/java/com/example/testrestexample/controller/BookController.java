package com.example.testrestexample.controller;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController{
   private final BookService bookService;

   @GetMapping("")
   public ResponseEntity<List<Book>> getAllBooks() {
      log.info("Received request to get all books");
      return ResponseEntity.ok(bookService.getAllBooks());
   }

   @GetMapping("/{bookId}")
   public ResponseEntity<Book> getBook(@PathVariable Long bookId){
      log.info("Received request to get book with id {}", bookId);
      return ResponseEntity.of(bookService.getBookById(bookId));
   }

   @PostMapping("")
   public ResponseEntity<Book> createBook(@RequestBody Book book){
      log.info("Received request to create a book: {}", book);
      return ResponseEntity.ok(bookService.createBook(book));
   }

   @PutMapping("/{bookId}")
   public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
      log.info("Received request to update book with id {}", bookId);
      return bookService.updateBook(bookId, book)
            .map(updatedBook -> {
               log.info("Successfully updated book with id {}", bookId);
               return ResponseEntity.ok(updatedBook);
            })
            .orElseGet(() -> {
               log.warn("Book with id {} not found", bookId);
               return ResponseEntity.notFound().build();
            });
   }

   @PatchMapping("/{bookId}")
   public ResponseEntity<Book> partialUpdateBook(@PathVariable Long bookId, @RequestBody Book book){
      log.info("Received request to partial update book with id {}", bookId);
      return bookService.partialUpdateBook(bookId,book)
            .map(ResponseEntity::ok)
            .orElseGet(()->ResponseEntity.notFound().build());
   }

   @DeleteMapping("/{bookId}")
   public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
      log.info("Received request to delete book with id {}", bookId);
      if (bookService.deleteBookById(bookId)) {
         log.info("Successfully deleted book with id {}", bookId);
         return ResponseEntity.noContent().build();
      }
      log.warn("Book with id {} not found", bookId);
      return ResponseEntity.notFound().build();
   }
}