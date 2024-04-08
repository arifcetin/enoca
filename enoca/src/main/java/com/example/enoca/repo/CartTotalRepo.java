package com.example.enoca.repo;

import com.example.enoca.entity.CartTotal;
import com.example.enoca.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartTotalRepo extends JpaRepository<CartTotal, Long> {
    CartTotal findCartTotalByCustomer(Customer customer);
    void deleteByCustomer(Customer customer);
}
