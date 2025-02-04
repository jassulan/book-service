package com.example.testrestexample.service;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

   private final BookRepository bookRepository;

   public Optional<Book> getBookById(Long bookId){
      return bookRepository.findById(bookId);
   }

   public Book createBook(Book book){
      return bookRepository.save(book);
   }

   public Optional<Book> updateBook(Long bookId, Book book){
      return bookRepository.findById(bookId)
            .map(existedBook -> {
               existedBook.setAuthor(book.getAuthor());
               existedBook.setTitle(book.getTitle());
               return bookRepository.save(existedBook);
            });
   }

   public Optional<Book> partiallyUpdateBook(Long bookId, Book book){
      return bookRepository.findById(bookId)
            .map(existedBook->{
               if(book.getAuthor() != null)
                  existedBook.setAuthor(book.getAuthor());
               if(book.getTitle() != null)
                  existedBook.setTitle(book.getTitle());
               return bookRepository.save(existedBook);
            });
   }

   public boolean deleteBook(Long bookId){
      return bookRepository.findById(bookId)
            .map(book->{
               bookRepository.delete(book);
               return true;
            }).orElse( false);
   }
}
