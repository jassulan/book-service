package com.example.bookservice.controller;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

   @Mock
   private BookService bookService;

   @InjectMocks
   private BookController bookController;

   private MockMvc mockMvc;
   private BookResponseDTO bookResponseDTO;
   private BookRequestDTO bookRequestDTO;

   @BeforeEach
   void setUp() {
      mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
      bookResponseDTO = new BookResponseDTO(1L, "Clean Code", "Robert C. Martin", null);
      bookRequestDTO = new BookRequestDTO("Clean Code", "Robert C. Martin");
   }

   @Test
   void getBookById_ShouldReturnBook_WhenExists() throws Exception {
      when(bookService.getBookById(1L)).thenReturn(Optional.of(bookResponseDTO));

      mockMvc.perform(get("/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Clean Code"))
            .andExpect(jsonPath("$.author").value("Robert C. Martin"));

      verify(bookService, times(1)).getBookById(1L);
   }

   @Test
   void getAllBooks_ShouldReturnPaginatedList() throws Exception {
      Page<BookResponseDTO> bookPage = new PageImpl<>(List.of(bookResponseDTO), PageRequest.of(0, 10), 1);
      when(bookService.getAllBooks(0, 10)).thenReturn(bookPage);

      mockMvc.perform(get("/books?page=0&size=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].title").value("Clean Code"));

      verify(bookService, times(1)).getAllBooks(0, 10);
   }

   @Test
   void createBook_ShouldReturnSuccessMessage() throws Exception {
      when(bookService.createBook(any(BookRequestDTO.class), eq(Locale.ENGLISH))).thenReturn("Book created successfully");

      mockMvc.perform(post("/books")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\"}")
                  .header("Accept-Language", "en"))
            .andExpect(status().isOk())
            .andExpect(content().string("Book created successfully"));

      verify(bookService, times(1)).createBook(any(BookRequestDTO.class), eq(Locale.ENGLISH));
   }
}