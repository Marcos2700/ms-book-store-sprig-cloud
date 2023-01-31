package com.example.store.shopping.shoppingservice.repository;

import com.example.store.shopping.shoppingservice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public List<Invoice> findByCustomerId(Long id);
    public Invoice findByNumberInvoice(String numberInvoice);
}
