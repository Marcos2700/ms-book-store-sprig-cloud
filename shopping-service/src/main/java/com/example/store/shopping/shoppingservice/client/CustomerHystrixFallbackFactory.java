package com.example.store.shopping.shoppingservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.store.shopping.shoppingservice.model.Customer;

@Component
public class CustomerHystrixFallbackFactory implements CustomerClient {

    @Override
    public ResponseEntity<Customer> getCustomer(Long id) {
        Customer customer = Customer.builder()
                                .firstname("null")
                                .lastname("null")
                                .email("null")
                                .photoUrl("null")
                                .build();
        return ResponseEntity.ok(customer);
    }
    
}
