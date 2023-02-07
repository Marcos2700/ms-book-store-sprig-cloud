package com.example.store.shopping.shoppingservice.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data @Builder
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
