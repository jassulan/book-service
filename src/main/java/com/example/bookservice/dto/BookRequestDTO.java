package com.example.bookservice.dto;

import jakarta.validation.constraints.NotBlank;

public record BookRequestDTO(
      @NotBlank(message = "Title cannot be empty") String title,
      @NotBlank(message = "Author cannot be empty") String author
) {}
