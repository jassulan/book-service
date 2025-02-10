package com.example.bookservice.controller;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "API for managing books")
public class BookController {
   private final BookService bookService;
   private final MessageSource messageSource;

   @Operation(summary = "Get all books", description = "Retrieves a paginated list of books")
   @GetMapping
   public ResponseEntity<Page<BookResponseDTO>> getAllBooks(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(bookService.getAllBooks(page, size));
   }

   @Operation(summary = "Get book by ID", description = "Retrieves details of a book by its ID")
   @GetMapping("/{bookId}")
   public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long bookId) {
      return ResponseEntity.of(bookService.getBookById(bookId));
   }

   @Operation(summary = "Create a new book", description = "Creates a book with the provided details")
   @PostMapping
   public ResponseEntity<String> createBook(
         @RequestBody @Valid BookRequestDTO bookDTO,
         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
      return ResponseEntity.ok(bookService.createBook(bookDTO, locale));
   }

   @Operation(summary = "Delete book", description = "Deletes a book by its ID")
   @DeleteMapping("/{bookId}")
   public ResponseEntity<String> deleteBook(
         @PathVariable Long bookId,
         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
      if (bookService.deleteBookById(bookId, locale)) {
         return ResponseEntity.ok(messageSource.getMessage("book.deleted", new Object[]{bookId}, locale));
      }
      return ResponseEntity.status(404).body(messageSource.getMessage("book.not.found", new Object[]{bookId}, locale));
   }
}