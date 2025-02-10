package com.example.testrestexample.service;

import com.example.testrestexample.model.Book;
import com.example.testrestexample.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService{
   private final BookRepository bookRepository;

   public List<Book> getAllBooks() {
      return (List<Book>) bookRepository.findAll();
   }

   public Optional<Book> getBookById(Long bookId){
      return bookRepository.findById(bookId);
   }

   public Book createBook(Book book){
      return bookRepository.save(book);
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

   public boolean deleteBookById(Long bookId){
      return bookRepository.findById(bookId)
            .map(existedBook->{
               bookRepository.delete(existedBook);
               return true;
            }).orElse(false);
   }
}