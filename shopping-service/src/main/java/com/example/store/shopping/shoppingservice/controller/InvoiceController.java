package com.example.store.shopping.shoppingservice.controller;


import com.example.store.shopping.shoppingservice.entity.Invoice;
import com.example.store.shopping.shoppingservice.repository.InvoiceRepository;
import com.example.store.shopping.shoppingservice.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @GetMapping
    public ResponseEntity<List<Invoice>> listAllInvoices(){
        List<Invoice> invoices = service.findInvoiceAll();

        if(invoices.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Long id){
        log.info("Fetching invoice with id {}", id);
        Invoice invoice = service.getInvoice(id);

        if(invoice == null){
            log.warn("Invoice with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result){
        log.info("Creating invoice: {}", invoice);
         if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
         }

         Invoice invoiceDB = service.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDB);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage message = ErrorMessage.builder()
                .code("2797")
                .messages(errors).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try{
            jsonString = mapper.writeValueAsString(message);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice, @PathVariable("id") Long id){
        log.info("Updating invoice with id {}", id);

        invoice.setId(id);
        Invoice currentInvoice = service.updateInvoice(invoice);

        if(currentInvoice == null){
            log.warn("Impossible to update, invoice with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(currentInvoice);
    }

    public ResponseEntity<Invoice> deleteInvoice(Long id){
        log.info("Fetching and deleting invoice with id {}", id);

        Invoice invoice = service.getInvoice(id);
        if(invoice == null){
            log.warn("Unable to delete. Invoice with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        invoice = service.deleteInvoice(invoice);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(invoice);
    }

}
