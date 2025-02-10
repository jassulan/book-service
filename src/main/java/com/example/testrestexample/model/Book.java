package com.example.testrestexample.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Data;

@Data
@Entity
public class Book{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String title;
   private String author;
   private Date createdAt = new Date();
}