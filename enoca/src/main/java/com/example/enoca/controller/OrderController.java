package com.example.enoca.controller;

import com.example.enoca.Dto.CartResponse;
import com.example.enoca.Dto.OrderResponse;
import com.example.enoca.entity.OrderC;
import com.example.enoca.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public ResponseEntity<OrderResponse> placeOrder(HttpServletRequest httpServletRequest){
        return orderService.placeOrder(httpServletRequest);
    }
    @GetMapping("/get")
    public ResponseEntity<OrderResponse> getOrderForCode(HttpServletRequest httpServletRequest){
        return orderService.getOrderForCode(httpServletRequest);
    }

    @GetMapping("/all")
    public List<OrderC> getAllOrdersForCustomer(){
        return orderService.getAllOrdersForCustomer();
    }
}
