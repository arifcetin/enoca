package com.example.enoca.repo;

import com.example.enoca.entity.Customer;
import com.example.enoca.entity.OrderC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderC, Long> {
    List<OrderC> findOrderCsByCustomer(Customer customer);
    @Query(value = "SELECT SUM(total_product_price) FROM orderc WHERE customer_id = :customer_id",
            nativeQuery = true)
    Float sumTotalProduct(@Param("customer_id") long customerId);
}
