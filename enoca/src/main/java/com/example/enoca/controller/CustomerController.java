package com.example.enoca.controller;

import com.example.enoca.entity.Customer;
import com.example.enoca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer newCustomer){
        return customerService.addCustomer(newCustomer);
    }

    @GetMapping
    public List<Customer> getCustomer(){
        return customerService.getAllCustomer();
    }


}
