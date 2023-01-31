package com.example.store.shopping.shoppingservice.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "invoice_items")
public class InvoiceItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive

    private Integer quantity;
    private Double price;

    @Column(name = "book_id")
    private Long bookId;

    @Transient
    private Double subtotal;

    public InvoiceItems() {
        this.quantity = 0;
        this.price = 0.0;
    }

    public Double getSubtotal(){
        if(this.quantity > 0 && this.price > 0){
            return this.quantity * this.price;
        }
        else{
            return  0.0;
        }


    }


}
