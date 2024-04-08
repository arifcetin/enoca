package com.example.enoca.service;

import com.example.enoca.entity.Product;
import com.example.enoca.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CartService cartService;
    @Autowired
    public ProductService(ProductRepo productRepo, CartService cartService) {
        this.productRepo = productRepo;
        this.cartService = cartService;
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product updateProduct(Product product, Long productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isPresent()){
            optionalProduct.get().setpName(product.getpName());
            optionalProduct.get().setPrice(product.getPrice());
            optionalProduct.get().setStock(product.getStock());
            productRepo.save(optionalProduct.get());
            cartService.updateCart(productId);
            return optionalProduct.get();
        }
        else
            return null;
    }

    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId);
    }
}
