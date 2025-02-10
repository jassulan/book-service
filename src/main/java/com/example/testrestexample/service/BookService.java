package com.example.testrestexample.service;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.repository.BookRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService{
   private final BookRepository bookRepository;
   private final MessageSource messageSource;

   public List<Book> getAllBooks() {
      return (List<Book>) bookRepository.findAll();
   }

   public Optional<Book> getBookById(Long bookId){
      return bookRepository.findById(bookId);
   }

   public String createBook(Book book, Locale locale) {
      log.info("Creating a new book: {}", book);
      bookRepository.save(book);
      return messageSource.getMessage("book.created", new Object[]{book.getTitle(), book.getAuthor()}, locale);
   }

   public Optional<Book> updateBook(Long bookId, Book book){
      return bookRepository.findById(bookId)
            .map(existedBook->{
               existedBook.setTitle(book.getTitle());
               existedBook.setAuthor(book.getAuthor());
               return bookRepository.save(existedBook);
            });
   }

   public Optional<Book> partialUpdateBook(Long bookId, Book book){
      return bookRepository.findById(bookId)
            .map(existedBook->{
               if(book.getTitle()!=null)
                  existedBook.setTitle(book.getTitle());
               if(book.getAuthor()!=null)
                  existedBook.setAuthor(book.getAuthor());
               return bookRepository.save(existedBook);
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