package com.example.store.book;

import com.example.store.book.entity.Book;
import com.example.store.book.entity.Category;
import com.example.store.book.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class BookRepositoryMockTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByCategory_thenBookList(){
        Book book01 = Book.builder()
                .category(Category.builder().id(1L).build())
                .title("Example4")
                .author("Example4")
                .price(Double.parseDouble("20.95"))
                .stock(25)
                .createAt(new Date())
                .status("CREATED")
                .build();
        bookRepository.save(book01);

        List<Book> founded = bookRepository.findByCategory(book01.getCategory());

        Assertions.assertEquals(founded.size(), 2);

    }
}
