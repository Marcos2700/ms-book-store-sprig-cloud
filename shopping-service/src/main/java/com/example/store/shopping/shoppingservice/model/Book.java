package com.example.store.shopping.shoppingservice.model;

import java.util.Date;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private String status;
    private Date createAt;
    private Category category;
}
