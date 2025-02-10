package com.example.bookservice.controller;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.service.BookService;
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
public class BookController {
   private final BookService bookService;
   private final MessageSource messageSource;

   @GetMapping
   public ResponseEntity<Page<BookResponseDTO>> getAllBooks(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size) {
      return ResponseEntity.ok(bookService.getAllBooks(page, size));
   }

   @GetMapping("/{bookId}")
   public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long bookId) {
      return ResponseEntity.of(bookService.getBookById(bookId));
   }

   @PostMapping
   public ResponseEntity<String> createBook(
         @RequestBody @Valid BookRequestDTO bookDTO,
         @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
      return ResponseEntity.ok(bookService.createBook(bookDTO, locale));
   }

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