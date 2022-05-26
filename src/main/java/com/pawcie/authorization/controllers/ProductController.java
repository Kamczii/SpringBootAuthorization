package com.pawcie.authorization.controllers;

import com.pawcie.authorization.entities.Product;
import com.pawcie.authorization.services.ProductService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="products")
@Data
public class ProductController {
    @Autowired
    private final ProductService productService;

    @GetMapping("/all")
    public List<Product> getProducts() {
        return  productService.getProducts();
    }

    @GetMapping("/published")
    public List<Product> getPublishedProducts(){
        return productService.getPublishedProducts();
    }

    @GetMapping("/published/{id}")
    public Product getPublishedProductById(@PathVariable("id") Integer id){
        return productService.getProductById(id, 1);
    }

    @GetMapping("/unpublished")
    public List<Product> getUnpublishedProducts(){
        return productService.getUnpublishedProducts();
    }

    @GetMapping("/unpublished/{id}")
    public Product getUnpublishedProductById(@PathVariable("id") Integer id){
        return productService.getProductById(id,0);
    }
}
