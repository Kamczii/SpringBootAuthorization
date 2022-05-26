package com.pawcie.authorization.products;

import com.pawcie.authorization.users.User;
import com.pawcie.authorization.utilities.ForbiddenException;
import com.pawcie.authorization.utilities.TypeProduct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Service
@NoArgsConstructor
public class ProductService {

    public List<Product> getProducts(){
        return List.of(

                Product.builder()
                        .id(0)
                        .type(TypeProduct.TEE)
                        .size("L") // TODO enums for sizecharts
                        .amountInStock((short)100) // TODO get from storage (?)
                        .info("Traditional tee for young bully guys")
                        .published(0)
                        .build(),

                Product.builder()
                        .id(1)
                        .type(TypeProduct.SHOES)
                        .size("42") // TODO enums for sizecharts
                        .amountInStock((short) 100) // TODO get from storage (?)
                        .info("Traditional shoes for young bully guys")
                        .published(1)
                        .build()

        );
    }

    public List<Product> getPublishedProducts(){
        List<Product> published = new LinkedList<>();
        for(Product p : getProducts()){
            if(p.getPublished() == 1){ published.add(p); }
        }
        return published;
    }

    public List<Product> getUnpublishedProducts(){
        List<Product> unpublished = new LinkedList<>();
        for(Product p : getProducts()){
            if(p.getPublished() == 0){ unpublished.add(p); }
        }
        return unpublished;
    }

    public Product getProductById(Integer id, Integer published){
        return getProducts().stream()
                .filter(p -> id.equals(p.getId()))
                .filter(p -> published.equals(p.getPublished()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product "+id+" does not exists"));
    }

}
