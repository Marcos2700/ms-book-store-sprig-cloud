package com.example.store.customer.customer.service.service;

import com.example.store.customer.customer.service.repository.CustomerRepository;
import com.example.store.customer.customer.service.repository.entity.Customer;
import com.example.store.customer.customer.service.repository.entity.Region;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
