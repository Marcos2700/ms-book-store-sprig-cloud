package com.example.store.shopping.shoppingservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.store.shopping.shoppingservice.model.Book;

@Component
public class BookHystrixFallbackFactory implements BookClient {

    @Override
    public ResponseEntity<Book> getBook(Long id) {
        Book book = this.getFallbackBook();

        return ResponseEntity.ok(book);
    }

    @Override
    public ResponseEntity<Book> updateStockBook(Long id, Integer quantity) {
        Book book = this.getFallbackBook();

        return ResponseEntity.ok(book);
    }

    private Book getFallbackBook(){
        Book book = Book.builder()
                        .title("none")
                        .author("none")
                        .status("none")
                        .stock(0)
                        .price(0.0)
                        .build();
        return book;
    }
    
}
