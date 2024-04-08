package com.example.enoca.repo;

import com.example.enoca.entity.Customer;
import com.example.enoca.entity.OrderC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderC, Long> {
    List<OrderC> findOrderCsByCustomer(Customer customer);
}
