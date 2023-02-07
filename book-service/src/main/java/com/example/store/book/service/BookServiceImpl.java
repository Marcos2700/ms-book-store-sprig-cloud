package com.example.store.book.service;

import com.example.store.book.entity.Book;
import com.example.store.book.entity.Category;
import com.example.store.book.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public List<Book> listAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book createBook(Book book) {
        book.setStatus("CREATED");
        book.setCreateAt(new Date());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book bookValidate = getBook(book.getId());
        if(bookValidate == null){
            return null;
        }
        bookValidate.setTitle(book.getTitle());
        bookValidate.setAuthor(book.getAuthor());
        bookValidate.setCategory(book.getCategory());
        bookValidate.setPrice(book.getPrice());
        return bookRepository.save(bookValidate);
    }

    @Override
    public Book deleteBook(Long id) {
        Book bookValidate = getBook(id);
        if(bookValidate == null){
            return null;
        }
        bookValidate.setStatus("DELETED");
        return bookRepository.save(bookValidate);
    }

    @Override
    public List<Book> findByCategoty(Category category) {
        return bookRepository.findByCategory(category);
    }

    @Override
    public Book updateStock(Long id, Integer quantity) {
        Book bookValidate = getBook(id);
        if(bookValidate == null){
            return null;
        }
        Integer stock = bookValidate.getStock() + quantity;
        bookValidate.setStock(stock);
        return bookRepository.save(bookValidate);
    }
}
