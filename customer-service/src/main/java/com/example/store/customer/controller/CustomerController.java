package com.example.store.customer.controller;

import com.example.store.customer.repository.entity.Customer;
import com.example.store.customer.repository.entity.Region;
import com.example.store.customer.service.CustomerService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId", required = false) Long regionId){
        List<Customer> customers = new ArrayList<>();
        if(regionId == null){
            customers = service.findCustomerAll();
            if (customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            Region regionUtil = new Region();
            regionUtil.setId(regionId);
            customers = service.findCustomerByRegion(regionUtil);
            if(customers == null){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        log.info("Fetching customer with id: {}", id);
        Customer customer = service.getCustomer(id);
        if(customer == null){
            log.error("Customer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating customer: {}", customer);
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }

        Customer createdCustomer = service.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());


        ErrorMessage message = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

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
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        log.info("Updating customer with id {id}" , id);

        Customer currentCustomer = service.getCustomer(id);

        if(customer == null){
            log.error("Unable to update. Customer with id {id} not found", id);
            return ResponseEntity.notFound().build();
        }

        customer.setId(id);
        currentCustomer = service.updateCustomer(customer);
        return ResponseEntity.ok(currentCustomer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        log.info("Fetching and deleting customer with id {id}", id);

        Customer customer = service.getCustomer(id);

        if(customer == null){
            log.error("Unable to delete. Customer with id {id} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(service.deleteCustomer(customer));
    }
}
