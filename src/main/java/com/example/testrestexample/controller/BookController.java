package com.example.testrestexample.controller;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.service.BookService;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController{
   private final BookService bookService;
   private final MessageSource messageSource;

   @GetMapping("")
   public ResponseEntity<Page<Book>> getAllBooks(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size) {
      log.info("Received request to get paginated books: page={}, size={}", page, size);
      return ResponseEntity.ok(bookService.getAllBooks(page, size));
   }

   @GetMapping("/{bookId}")
   public ResponseEntity<Book> getBook(@PathVariable Long bookId){
      log.info("Received request to get book with id {}", bookId);
      return ResponseEntity.of(bookService.getBookById(bookId));
   }

   @PostMapping("")
   public ResponseEntity<String> createBook(@RequestBody Book book,
         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
      return ResponseEntity.ok(bookService.createBook(book, locale));
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
   public ResponseEntity<String> deleteBook(@PathVariable Long bookId, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
      if (bookService.deleteBookById(bookId, locale)) {
         return ResponseEntity.ok(messageSource.getMessage("book.deleted", new Object[]{bookId}, locale));
      }
      return ResponseEntity.status(404).body(messageSource.getMessage("book.not.found", new Object[]{bookId}, locale));
   }
}