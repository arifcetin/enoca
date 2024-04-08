package com.example.enoca.Dto;

import com.example.enoca.entity.OrderC;

import java.util.List;

public class OrderResponse {
    private String message;
    private List<OrderC> orderCList;
    private Float totalPrice;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderC> getOrderCList() {
        return orderCList;
    }

    public void setOrderCList(List<OrderC> orderCList) {
        this.orderCList = orderCList;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
