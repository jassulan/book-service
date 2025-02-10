package com.example.testrestexample.controller;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
      List<Book> books = Arrays.asList(book);
      when(bookService.getAllBooks()).thenReturn(books);

      mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].title").value("Clean Code"));

      verify(bookService, times(1)).getAllBooks();
   }

   @Test
   void createBook() throws Exception {
      when(bookService.createBook(any(Book.class))).thenReturn(book);

      mockMvc.perform(post("/books")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Clean Code"));

      verify(bookService, times(1)).createBook(any(Book.class));
   }

   @Test
   void deleteBook() throws Exception {
      when(bookService.deleteBookById(1L)).thenReturn(true);

      mockMvc.perform(delete("/books/1"))
            .andExpect(status().isNoContent());

      verify(bookService, times(1)).deleteBookById(1L);
   }

   @Test
   void deleteNotExistedBook() throws Exception {
      when(bookService.deleteBookById(1L)).thenReturn(false);

      mockMvc.perform(delete("/books/1"))
            .andExpect(status().isNotFound());

      verify(bookService, times(1)).deleteBookById(1L);
   }
}
