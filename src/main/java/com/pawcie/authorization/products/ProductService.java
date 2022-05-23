package com.pawcie.authorization.products;

import com.pawcie.authorization.utilities.TypeProduct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
                        .isPublished(false)
                        .build()
        );
    }

    public List<Product> getPublishedProducts(){
        List<Product> published = new LinkedList<>();
        for(Product p : getProducts()){
            if(p.isPublished()){ published.add(p); }
        }
        return published;
    }

    public List<Product> getUnpublishedProducts(){
        List<Product> unpublished = new LinkedList<>();
        for(Product p : getProducts()){
            if(!p.isPublished()){ unpublished.add(p); }
        }
        return unpublished;
    }

}
