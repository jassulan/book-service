package com.example.testrestexample.service;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.repository.BookRepository;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

   @Mock
   private BookRepository bookRepository;

   @InjectMocks
   private BookService bookService;

   private Book book;

   @BeforeEach
   void setUp() {
      book = new Book();
      book.setId(1L);
      book.setTitle("Clean Code");
      book.setAuthor("Robert C. Martin");
   }

   @Test
   void getBookById() {
      when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

      Optional<Book> result = bookService.getBookById(1L);

      assertTrue(result.isPresent());
      assertEquals("Clean Code", result.get().getTitle());
      verify(bookRepository, times(1)).findById(1L);
   }

   @Test
   void getAllBooks() {
      List<Book> books = Arrays.asList(book);
      when(bookRepository.findAll()).thenReturn(books);

      List<Book> result = bookService.getAllBooks();

      assertEquals(1, result.size());
      assertEquals("Clean Code", result.get(0).getTitle());
      verify(bookRepository, times(1)).findAll();
   }

   @Test
   void createBook() {
      when(bookRepository.save(any(Book.class))).thenReturn(book);

      String result = bookService.createBook(book, Locale.ENGLISH);

      assertNotNull(result);
      assertTrue(result.contains("Clean Code"));
      verify(bookRepository, times(1)).save(any(Book.class));
   }

   @Test
   void updateBook() {
      Book updatedBook = new Book();
      updatedBook.setTitle("Refactoring");
      updatedBook.setAuthor("Martin Fowler");

      when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
      when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

      Optional<Book> result = bookService.updateBook(1L, updatedBook);

      assertTrue(result.isPresent());
      assertEquals("Refactoring", result.get().getTitle());
      verify(bookRepository, times(1)).findById(1L);
      verify(bookRepository, times(1)).save(any(Book.class));
   }

   @Test
   void deleteBookById() {
      when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

      boolean result = bookService.deleteBookById(1L, Locale.ENGLISH);

      assertTrue(result);
      verify(bookRepository, times(1)).findById(1L);
      verify(bookRepository, times(1)).delete(book);
   }

   @Test
   void deleteNotExistedBookById() {
      when(bookRepository.findById(1L)).thenReturn(Optional.empty());

      boolean result = bookService.deleteBookById(1L, Locale.ENGLISH);

      assertFalse(result);
      verify(bookRepository, times(1)).findById(1L);
      verify(bookRepository, never()).delete(any(Book.class));
   }
}
