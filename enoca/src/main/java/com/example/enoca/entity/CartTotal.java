package com.example.enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CartTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartTotalId;
    private float totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    public CartTotal() {
    }

    public CartTotal(Long cartTotalId, float totalPrice, Customer customer) {
        this.cartTotalId = cartTotalId;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public Long getCartTotalId() {
        return cartTotalId;
    }

    public void setCartTotalId(Long cartTotalId) {
        this.cartTotalId = cartTotalId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
