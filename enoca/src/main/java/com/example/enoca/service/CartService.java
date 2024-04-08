package com.example.enoca.service;

import com.example.enoca.Dto.CartDto;
import com.example.enoca.Dto.CartResponse;
import com.example.enoca.entity.Cart;
import com.example.enoca.entity.CartTotal;
import com.example.enoca.entity.Customer;
import com.example.enoca.entity.Product;
import com.example.enoca.repo.CartRepo;
import com.example.enoca.repo.CartTotalRepo;
import com.example.enoca.repo.CustomerRepo;
import com.example.enoca.repo.ProductRepo;
import com.example.enoca.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final CartTotalRepo cartTotalRepo;
    @Autowired
    public CartService(CartRepo cartRepo, ProductRepo productRepo, CustomerRepo customerRepo, JwtTokenProvider jwtTokenProvider, CartTotalRepo cartTotalRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cartTotalRepo = cartTotalRepo;
    }
    public ResponseEntity<CartResponse> addProductToCart(CartDto cartDto, HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        Long customerId = jwtTokenProvider.getUserIdFromJwt(bearer.substring("Bearer".length()+1));
        Optional<Customer> customer = customerRepo.findById(customerId);
        Optional<Product> product = productRepo.findById(cartDto.getpId());
        CartResponse cartResponse = new CartResponse();
        if (cartDto.getAmount() <= product.get().getStock()){
            product.get().setStock(product.get().getStock()-cartDto.getAmount());
            productRepo.save(product.get());
            if (customer.isPresent()){
                Cart cart = cartRepo.findCartByPrIdAndCustomer(cartDto.getpId(),customer.get());
                if(cart != null){
                    cart.setAmount(cart.getAmount()+cartDto.getAmount());
                    cart.setTotalProductPrice(cart.getTotalProductPrice()+(cartDto.getAmount()*productRepo.findById(cartDto.getpId()).get().getPrice()));
                    cartRepo.save(cart);
                    cartResponse.setMessage("product added");
                    cartResponse.setCart(getCarts(httpServletRequest).getBody().getCart());
                    cartResponse.setTotalPrice(cartTotal(customer.get()).getTotalPrice());
                    return new ResponseEntity<>(cartResponse, HttpStatus.OK);
                }
                Cart newCart = createNewCart(customer,cartDto);
                cartRepo.save(newCart);
                cartTotal(customer.get());
                cartResponse.setMessage("new product added");
                cartResponse.setCart(getCarts(httpServletRequest).getBody().getCart());
                cartResponse.setTotalPrice(cartTotal(customer.get()).getTotalPrice());
                return new ResponseEntity<>(cartResponse, HttpStatus.OK);
            }
        }
        cartResponse.setMessage("not enough stock");
        cartResponse.setCart(null);
        return new ResponseEntity<>(cartResponse, HttpStatus.BAD_REQUEST);
    }
    public Cart createNewCart(Optional<Customer> customer, CartDto cartDto){
        Cart cart = cartRepo.findCartByCustomer(customer);
        Cart newCart = new Cart();
        newCart.setCustomer(customer.get());
        newCart.setPrId(cartDto.getpId());
        newCart.setAmount(cartDto.getAmount());
        newCart.setPrice(productRepo.findById(cartDto.getpId()).get().getPrice());
        newCart.setTotalProductPrice(cartDto.getAmount()*productRepo.findById(cartDto.getpId()).get().getPrice());
        return newCart;
    }

    public CartTotal cartTotal(Customer customer){
        CartTotal cartTotal = cartTotalRepo.findCartTotalByCustomer(customer);
        if (cartTotal==null){
            CartTotal newCartTotal = new CartTotal();
            newCartTotal.setCustomer(customer);
            newCartTotal.setTotalPrice(cartRepo.sumTotalProduct(customer.getCustomerId()));
            cartTotalRepo.save(newCartTotal);
            return newCartTotal;
        }
        else {
            cartTotal.setCustomer(customer);
            cartTotal.setTotalPrice(cartRepo.sumTotalProduct(customer.getCustomerId()));
            cartTotalRepo.save(cartTotal);
            return cartTotal;
        }
    }


    public ResponseEntity<CartResponse> removeProductFromCart(CartDto cartDto, HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        Long customerId = jwtTokenProvider.getUserIdFromJwt(bearer.substring("Bearer".length()+1));
        Optional<Customer> customer = customerRepo.findById(customerId);
        Optional<Product> product = productRepo.findById(cartDto.getpId());
        Cart cart = cartRepo.findCartByPrIdAndCustomer(cartDto.getpId(),customer.get());
        CartTotal cartTotal = cartTotalRepo.findCartTotalByCustomer(customer.get());
        CartResponse cartResponse = new CartResponse();
        if(cart.getAmount()==cartDto.getAmount()){
            cartTotal.setTotalPrice(cartTotal.getTotalPrice() - (cartDto.getAmount() * product.get().getPrice()));
            cartTotalRepo.save(cartTotal);
            cartRepo.deleteById(cart.getCartId());
            cartResponse.setMessage("product deleted");
            cartResponse.setTotalPrice(cartTotal.getTotalPrice());
            cartResponse.setCart(getCarts(httpServletRequest).getBody().getCart());
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        else {
            cart.setAmount(cart.getAmount()-cartDto.getAmount());
            cart.setTotalProductPrice(cart.getTotalProductPrice()-(cartDto.getAmount()*product.get().getPrice()));
            cartTotal.setTotalPrice(cartTotal.getTotalPrice() - (cartDto.getAmount() * product.get().getPrice()));
            cartRepo.save(cart);
            cartTotalRepo.save(cartTotal);
            cartResponse.setMessage("product deleted");
            cartResponse.setTotalPrice(cartTotal.getTotalPrice());
            cartResponse.setCart(getCarts(httpServletRequest).getBody().getCart());
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
    }

    public ResponseEntity<CartResponse> getCarts(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        Long customerId = jwtTokenProvider.getUserIdFromJwt(bearer.substring("Bearer".length()+1));
        Optional<Customer> customer = customerRepo.findById(customerId);
        CartTotal cartTotal = cartTotalRepo.findCartTotalByCustomer(customer.get());
        CartResponse cartResponse = new CartResponse();
        List<Cart> carts = cartRepo.findCartsByCustomer(customer.get());
        if(cartTotal!=null){
            cartResponse.setMessage("Customers cart");
            cartResponse.setCart(carts);
            cartResponse.setTotalPrice(cartTotal.getTotalPrice());
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        else {
            cartResponse.setMessage("Customers cart not found");
            cartResponse.setCart(null);
            cartResponse.setTotalPrice(null);
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
    }
    @Transactional
    public ResponseEntity<CartResponse> emptyCart(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader("Authorization");
        Long customerId = jwtTokenProvider.getUserIdFromJwt(bearer.substring("Bearer".length()+1));
        Optional<Customer> customer = customerRepo.findById(customerId);
        CartTotal cartTotal = cartTotalRepo.findCartTotalByCustomer(customer.get());
        CartResponse cartResponse = new CartResponse();
        if(cartTotal!=null){
            cartRepo.deleteByCustomer(customer.get());
            cartTotalRepo.deleteByCustomer(customer.get());
            cartResponse.setMessage("Cart deleted");
            cartResponse.setCart(null);
            cartResponse.setTotalPrice(null);
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
        else {
            cartResponse.setMessage("Customers cart not found");
            cartResponse.setCart(null);
            cartResponse.setTotalPrice(null);
            return new ResponseEntity<>(cartResponse,HttpStatus.OK);
        }
    }

    public void updateCart(Long pId){
        Cart cart = cartRepo.findCartByPrId(pId);
        if(cart!=null) {
            Customer customer = cart.getCustomer();
            Optional<Product> product = productRepo.findById(pId);
            CartTotal cartTotal = cartTotalRepo.findCartTotalByCustomer(customer);
            cart.setPrice(product.get().getPrice());
            cart.setTotalProductPrice(cart.getAmount() * cart.getPrice());
            cartRepo.save(cart);
            cartTotal(customer);
        }
    }
}
