package com.example.enoca.repo;

import com.example.enoca.entity.Cart;
import com.example.enoca.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findCartByPrIdAndCustomer(Long aLong, Customer customer);

    Cart findCartByCustomer(Optional<Customer> customer);

    @Query(value = "SELECT SUM(total_product_price) FROM cart WHERE customer_id = :customer_id",
            nativeQuery = true)
    float sumTotalProduct(@Param("customer_id") Long customerId);

    List<Cart> findCartsByCustomer(Customer customer);
    void deleteByCustomer(Customer customer);

    Cart findCartByPrId(Long pId);
}
