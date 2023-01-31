package com.example.store.book.service;


import com.example.store.book.entity.Book;
import com.example.store.book.entity.Category;

import java.util.List;

public interface BookService {
    public List<Book> listAllBook();
    public Book getBook(Long id);
    public Book createBook(Book book);
    public Book updateBook(Book book);
    public Book deleteBook(Long id);
    public List<Book> findByCategoty(Category category);
    public Book updateStock(Long id, Integer quantity);
}
