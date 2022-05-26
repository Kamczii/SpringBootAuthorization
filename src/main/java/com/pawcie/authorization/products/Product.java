package com.pawcie.authorization.products;

import com.pawcie.authorization.utilities.TypeProduct;
import lombok.*;

@Builder
@Data
public class Product {
    @NonNull
    private int id;
    @NonNull
    private TypeProduct type;
    @NonNull
    private String size;
    @NonNull
    private short amountInStock;
    @NonNull
    private String info;
    @NonNull
    private Integer published;
}
