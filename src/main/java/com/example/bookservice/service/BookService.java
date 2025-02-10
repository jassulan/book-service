package com.example.bookservice.service;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional // Ensures atomic database operations
public class BookService {
   private final BookRepository bookRepository;
   private final MessageSource messageSource;

   public Page<BookResponseDTO> getAllBooks(int page, int size) {
      log.info("Fetching books with pagination: page={}, size={}", page, size);
      Pageable pageable = PageRequest.of(page, size);
      return bookRepository.findAll(pageable).map(BookResponseDTO::from);
   }

   public Optional<BookResponseDTO> getBookById(Long bookId) {
      return bookRepository.findById(bookId).map(BookResponseDTO::from);
   }

   public String createBook(BookRequestDTO bookDTO, Locale locale) {
      log.info("Creating a new book: {}", bookDTO);

      // Prevent duplicate book titles
      if (bookRepository.findByTitle(bookDTO.title()).isPresent()) {
         throw new IllegalArgumentException("A book with this title already exists.");
      }

      Book book = Book.builder()
            .title(bookDTO.title())
            .author(bookDTO.author())
            .build();

      bookRepository.save(book);

      return messageSource.getMessage("book.created", new Object[]{book.getTitle(), book.getAuthor()}, locale);
   }

   public Optional<BookResponseDTO> updateBook(Long bookId, BookRequestDTO bookDTO) {
      return bookRepository.findById(bookId)
            .map(existingBook -> {
               existingBook.setTitle(bookDTO.title());
               existingBook.setAuthor(bookDTO.author());
               return BookResponseDTO.from(bookRepository.save(existingBook));
            });
   }

   public Optional<BookResponseDTO> partialUpdateBook(Long bookId, BookRequestDTO bookDTO) {
      return bookRepository.findById(bookId)
            .map(existingBook -> {
               if (bookDTO.title() != null) {
                  existingBook.setTitle(bookDTO.title());
               }
               if (bookDTO.author() != null) {
                  existingBook.setAuthor(bookDTO.author());
               }
               return BookResponseDTO.from(bookRepository.save(existingBook));
            });
   }

   public boolean deleteBookById(Long bookId, Locale locale) {
      log.info("Deleting book with id {}", bookId);
      return bookRepository.findById(bookId)
            .map(existingBook -> {
               bookRepository.delete(existingBook);
               log.info(messageSource.getMessage("book.deleted", new Object[]{bookId}, locale));
               return true;
            })
            .orElseGet(() -> {
               log.warn(messageSource.getMessage("book.not.found", new Object[]{bookId}, locale));
               return false;
            });
   }
}