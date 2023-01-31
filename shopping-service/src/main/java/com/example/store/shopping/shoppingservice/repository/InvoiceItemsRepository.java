package com.example.store.shopping.shoppingservice.repository;

import com.example.store.shopping.shoppingservice.entity.InvoiceItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItems, Long> {
}
