package com.example.enoca.Dto;

import com.example.enoca.entity.Cart;

import java.util.List;

public class CartResponse {
    private String message;
    private List<Cart> cart;
    private Float totalPrice;

    public CartResponse() {
    }

    public CartResponse(String message, List<Cart> cart, Float totalPrice) {
        this.message = message;
        this.cart = cart;
        this.totalPrice = totalPrice;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
