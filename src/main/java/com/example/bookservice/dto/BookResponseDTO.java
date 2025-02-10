package com.example.bookservice.dto;

import com.example.bookservice.model.Book;
import java.time.LocalDateTime;

public record BookResponseDTO(Long id, String title, String author, LocalDateTime createdAt) {
   public static BookResponseDTO from(Book book) {
      return new BookResponseDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getCreatedAt()
      );
   }
}