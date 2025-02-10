package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.service.BookService;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

   @Mock
   private BookService bookService;

   @InjectMocks
   private BookController bookController;

   private MockMvc mockMvc;
   private Book book;

   @BeforeEach
   void setUp() {
      mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
      book = new Book();
      book.setId(1L);
      book.setTitle("Clean Code");
      book.setAuthor("Robert C. Martin");
   }

   @Test
   void getBookById() throws Exception {
      when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

      mockMvc.perform(get("/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Clean Code"))
            .andExpect(jsonPath("$.author").value("Robert C. Martin"));

      verify(bookService, times(1)).getBookById(1L);
   }

   @Test
   void getAllBooks() throws Exception {
      Pageable pageable = PageRequest.of(0, 10);
      List<Book> books = Arrays.asList(book);
      Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

      when(bookService.getAllBooks(0, 10)).thenReturn(bookPage);

      mockMvc.perform(get("/books?page=0&size=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].title").value("Clean Code"));

      verify(bookService, times(1)).getAllBooks(0, 10);
   }

   @Test
   void createBook() throws Exception {
      when(bookService.createBook(any(Book.class), eq(Locale.ENGLISH))).thenReturn("Book created successfully");

      mockMvc.perform(post("/books")
                  .header("Accept-Language", "en")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string("Book created successfully"));

      verify(bookService, times(1)).createBook(any(Book.class), eq(Locale.ENGLISH));
   }

   @Test
   void deleteBook() throws Exception {
      when(bookService.deleteBookById(1L, Locale.ENGLISH)).thenReturn(true);

      mockMvc.perform(delete("/books/1").header("Accept-Language", "en"))
            .andExpect(status().isNoContent());

      verify(bookService, times(1)).deleteBookById(1L, Locale.ENGLISH);
   }

   @Test
   void deleteNotExistedBook() throws Exception {
      when(bookService.deleteBookById(1L, Locale.ENGLISH)).thenReturn(false);

      mockMvc.perform(delete("/books/1").header("Accept-Language", "en"))
            .andExpect(status().isNotFound());

      verify(bookService, times(1)).deleteBookById(1L, Locale.ENGLISH);
   }
}
