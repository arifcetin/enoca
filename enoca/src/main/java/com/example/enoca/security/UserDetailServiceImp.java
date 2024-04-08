package com.example.enoca.security;

import com.example.enoca.entity.Customer;
import com.example.enoca.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    private final CustomerRepo customerRepo;
    @Autowired
    public UserDetailServiceImp(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByName(name);
        return JwtUserDetails.create(customer);
    }
    public UserDetails loadUserById(Long id){
        Customer customer = customerRepo.findById(id).get();
        return JwtUserDetails.create(customer);
    }
}
