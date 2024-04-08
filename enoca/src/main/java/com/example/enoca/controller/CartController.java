package com.example.enoca.controller;

import com.example.enoca.Dto.CartDto;
import com.example.enoca.Dto.CartResponse;
import com.example.enoca.entity.Cart;
import com.example.enoca.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody CartDto cartDto, HttpServletRequest httpServletRequest){
        return cartService.addProductToCart(cartDto, httpServletRequest);
    }
    @PostMapping("/delete")
    public ResponseEntity<CartResponse> removeProductFromCart(@RequestBody CartDto cartDto, HttpServletRequest httpServletRequest){
        return cartService.removeProductFromCart(cartDto,httpServletRequest);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCarts(HttpServletRequest httpServletRequest){
        return cartService.getCarts(httpServletRequest);
    }

    @DeleteMapping
    public ResponseEntity<CartResponse> emptyCart(HttpServletRequest httpServletRequest){
        return cartService.emptyCart(httpServletRequest);
    }

}
