package com.pawcie.authorization.controllers;

import com.pawcie.authorization.entities.Product;
import com.pawcie.authorization.services.ProductService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(path="products")
@Data
public class ProductController {
    @Autowired
    private final ProductService productService;

    @GetMapping("/all")
    public String getProducts(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return  "products";
    }

    @GetMapping("/published")
    public String getPublishedProducts(Model model){
        List<Product> products = productService.getPublishedProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/published/{id}")
    public String getPublishedProductById(@PathVariable("id") Integer id, Model model){
        Product product =  productService.getProductById(id, 1);
        model.addAttribute("products", Collections.singleton(product));
        return "products";
    }

    @GetMapping("/unpublished")
    public String getUnpublishedProducts(Model model){
        List<Product> products = productService.getUnpublishedProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/unpublished/{id}")
    public String getUnpublishedProductById(@PathVariable("id") Integer id, Model model){
        Product product =  productService.getProductById(id,0);
        model.addAttribute("products", Collections.singleton(product));
        return "products";
    }
}
