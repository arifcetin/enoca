package com.example.enoca.service;

import com.example.enoca.entity.Customer;
import com.example.enoca.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer addCustomer(Customer newCustomer) {
        return customerRepo.save(newCustomer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepo.findAll();
    }

    public Customer getOneCustomerByName(String customerName) {
        return customerRepo.getOneCustomerByName(customerName);
    }
}
