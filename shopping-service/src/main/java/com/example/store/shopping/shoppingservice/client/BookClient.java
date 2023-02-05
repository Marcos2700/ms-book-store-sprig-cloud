package com.example.store.shopping.shoppingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.store.shopping.shoppingservice.model.Book;

@FeignClient(name = "book-service")
@RequestMapping(value = "/books")
public interface BookClient {
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Book> updateStockBook(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Integer quantity);
}
