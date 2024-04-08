package com.example.enoca.repo;

import com.example.enoca.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByName(String name);

    Customer getOneCustomerByName(String name);
}
