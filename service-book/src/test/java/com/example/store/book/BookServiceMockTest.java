package com.example.store.book;

import com.example.store.book.entity.Book;
import com.example.store.book.entity.Category;
import com.example.store.book.repository.BookRepository;
import com.example.store.book.service.BookService;
import com.example.store.book.service.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class BookServiceMockTest {

    @Mock
    private BookRepository repository;

    private BookService service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        service = new BookServiceImpl(repository);

        Book book = Book.builder()
                .id(1L)
                .title("Example")
                .author("Example")
                .price(25.25)
                .status("CREATED")
                .createAt(new Date())
                .category(Category.builder().id(1L).name("Example").build())
                .stock(15)
                .build();

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(book));
    }

    @Test
    public void whenValidGetId_ThenReturnBook(){
        Book book = service.getBook(1L);
        Assertions.assertEquals(book.getTitle(), "Example");
    }

    @Test
    public void whenValidateUpdateStock_ThenReturnsAddingStock(){
        Book book = service.getBook(1L);
        service.updateStock(1L, 5);
        Assertions.assertEquals(book.getStock(), 20);
    }

    @Test
    public void whenValidateUpdateStock_ThenReturnsMinimizingStock(){
        Book book = service.getBook(1L);
        service.updateStock(1L, -5);
        Assertions.assertEquals(book.getStock(), 10);
    }

}
