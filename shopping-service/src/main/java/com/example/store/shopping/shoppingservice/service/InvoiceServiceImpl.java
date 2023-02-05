package com.example.store.shopping.shoppingservice.service;

import com.example.store.shopping.shoppingservice.client.BookClient;
import com.example.store.shopping.shoppingservice.client.CustomerClient;
import com.example.store.shopping.shoppingservice.entity.Invoice;
import com.example.store.shopping.shoppingservice.entity.InvoiceItems;
import com.example.store.shopping.shoppingservice.model.Book;
import com.example.store.shopping.shoppingservice.model.Customer;
//import com.example.store.shopping.shoppingservice.repository.InvoiceItemsRepository;
import com.example.store.shopping.shoppingservice.repository.InvoiceRepository;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    //@Autowired
    //private InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private BookClient bookClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());

        if(invoiceDB != null){
            return invoiceDB;
        }

        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);

        invoiceDB.getItems().forEach(invoiceItem -> {
            bookClient.updateStockBook(invoiceItem.getBookId(), invoiceItem.getQuantity() * -1);
        }
        );  

        return invoiceDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());

        if(invoiceDB == null){
            return null;
        }

        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());

        if(invoiceDB == null){
            return null;
        }

        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice =  invoiceRepository.findById(id).orElse(null);

        if(invoice != null){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItems> listItems = invoice.getItems().stream().map(invoiceItem -> {
                Book book = bookClient.getBook(invoiceItem.getBookId()).getBody();
                invoiceItem.setBook(book);
                return invoiceItem;
            }).collect(Collectors.toList());

            invoice.setItems(listItems);
        }

        return invoice;

    }
}
