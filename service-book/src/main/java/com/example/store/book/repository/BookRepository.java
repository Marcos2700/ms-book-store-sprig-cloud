package com.example.store.book.repository;

import com.example.store.book.entity.Book;
import com.example.store.book.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByCategory(Category category);
}
