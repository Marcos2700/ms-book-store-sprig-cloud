package com.example.store.customer.service;

//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store.customer.repository.CustomerRepository;
import com.example.store.customer.repository.entity.Customer;
import com.example.store.customer.repository.entity.Region;

import java.util.List;

//@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> findCustomerAll() {
        return repository.findAll();
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {
        return repository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer validCustomer = repository.findByNumberId(customer.getNumberId());
        if(validCustomer != null){
            return validCustomer;
        }
        customer.setState("CREATED");
        validCustomer = repository.save(customer);
        return validCustomer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer validCustomer = repository.findByNumberId(customer.getNumberId());
        if(validCustomer == null){
            return validCustomer;
        }
        validCustomer.setFirstname(customer.getFirstname());
        validCustomer.setLastname(customer.getLastname());
        validCustomer.setEmail(customer.getEmail());
        validCustomer.setPhotoUrl(customer.getPhotoUrl());

        return repository.save(validCustomer);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer validCustomer = repository.findByNumberId(customer.getNumberId());
        if(validCustomer == null){
            return validCustomer;
        }
        customer.setState("DELETED");

        return repository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return repository.findById(id).orElse(null);
    }

}
