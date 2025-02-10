package com.example.bookservice.service;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

   @Mock
   private BookRepository bookRepository;

   @Mock
   private MessageSource messageSource;

   @InjectMocks
   private BookService bookService;

   private Book book;
   private BookRequestDTO bookRequestDTO;

   @BeforeEach
   void setUp() {
      book = new Book(1L, "Clean Code", "Robert C. Martin", null);
      bookRequestDTO = new BookRequestDTO("Clean Code", "Robert C. Martin");
   }

   @Test
   void getAllBooks_ShouldReturnPaginatedList() {
      Page<Book> bookPage = new PageImpl<>(List.of(book));
      when(bookRepository.findAll(any(PageRequest.class))).thenReturn(bookPage);

      Page<BookResponseDTO> result = bookService.getAllBooks(0, 10);

      assertEquals(1, result.getTotalElements());
      assertEquals("Clean Code", result.getContent().get(0).title());
      verify(bookRepository, times(1)).findAll(any(PageRequest.class));
   }

   @Test
   void getBookById_ShouldReturnBook_WhenExists() {
      when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

      Optional<BookResponseDTO> result = bookService.getBookById(1L);

      assertTrue(result.isPresent());
      assertEquals("Clean Code", result.get().title());
      verify(bookRepository, times(1)).findById(1L);
   }

   @Test
   void getBookById_ShouldReturnEmpty_WhenNotFound() {
      when(bookRepository.findById(1L)).thenReturn(Optional.empty());

      Optional<BookResponseDTO> result = bookService.getBookById(1L);

      assertTrue(result.isEmpty());
      verify(bookRepository, times(1)).findById(1L);
   }

   @Test
   void createBook_ShouldSaveBook_WhenValidRequest() {
      when(bookRepository.findByTitle("Clean Code")).thenReturn(Optional.empty());
      when(bookRepository.save(any(Book.class))).thenReturn(book);
      when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
            .thenReturn("Book created successfully");

      String result = bookService.createBook(bookRequestDTO, Locale.ENGLISH);

      assertEquals("Book created successfully", result);
      verify(bookRepository, times(1)).save(any(Book.class));
   }

   @Test
   void createBook_ShouldThrowException_WhenTitleExists() {
      when(bookRepository.findByTitle("Clean Code")).thenReturn(Optional.of(book));

      Exception exception = assertThrows(IllegalArgumentException.class,
            () -> bookService.createBook(bookRequestDTO, Locale.ENGLISH));

      assertEquals("A book with this title already exists.", exception.getMessage());
      verify(bookRepository, never()).save(any(Book.class));
   }

   @Test
   void updateBook_ShouldUpdateBook_WhenExists() {
      when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
      when(bookRepository.save(any(Book.class))).thenReturn(book);

      Optional<BookResponseDTO> result = bookService.updateBook(1L, bookRequestDTO);

      assertTrue(result.isPresent());
      assertEquals("Clean Code", result.get().title());
      verify(bookRepository, times(1)).save(any(Book.class));
   }

   @Test
   void deleteBookById_ShouldReturnTrue_WhenBookExists() {
      when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

      boolean result = bookService.deleteBookById(1L, Locale.ENGLISH);

      assertTrue(result);
      verify(bookRepository, times(1)).delete(book);
   }

   @Test
   void deleteBookById_ShouldReturnFalse_WhenBookNotFound() {
      when(bookRepository.findById(1L)).thenReturn(Optional.empty());

      boolean result = bookService.deleteBookById(1L, Locale.ENGLISH);

      assertFalse(result);
      verify(bookRepository, never()).delete(any(Book.class));
   }
}
