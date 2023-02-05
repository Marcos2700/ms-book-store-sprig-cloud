package com.example.store.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.store.customer.repository.entity.Customer;
import com.example.store.customer.repository.entity.Region;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByNumberId(String numberId);
    public List<Customer> findByLastname(String lastname);
    public List<Customer> findByRegion(Region region);
}
