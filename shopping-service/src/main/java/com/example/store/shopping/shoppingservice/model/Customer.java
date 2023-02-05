package com.example.store.shopping.shoppingservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Long id;   
    private String numberId;
    private String firstname;
    private String lastname;
    private String email;
    private String photoUrl;
    private String state;
    private Region region;
}
