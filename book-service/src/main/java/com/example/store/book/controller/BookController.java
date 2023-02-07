package com.example.store.book.controller;

import com.example.store.book.entity.Book;
import com.example.store.book.entity.Category;
import com.example.store.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> listBooks(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Book> books = new ArrayList<>();
        if(categoryId == null){
            books = service.listAllBook();
            if(books.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            books = service.findByCategoty(Category.builder().id(categoryId).build());
            if(books.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
        Book book = service.getBook(id);
        if(book == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Book createdBook = service.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                            Map < String, String > error = new HashMap<>();
                            error.put(err.getField(), err.getDefaultMessage());
                            return error;
                        }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book){
        book.setId(id);
        Book updatedBook = service.updateBook(book);
        if(updatedBook == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id){
        Book deletedBook = service.deleteBook(id);
        if(deletedBook == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedBook);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Book> updateStockBook(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Integer quantity){
        Book book = service.updateStock(id, quantity);
        if(book == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
}
