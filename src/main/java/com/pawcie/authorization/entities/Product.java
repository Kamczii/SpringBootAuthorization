package com.pawcie.authorization.entities;

import com.pawcie.authorization.utilities.TypeProduct;
import lombok.*;

@Builder
@Getter
public class Product {

    private int id;
    private TypeProduct type;
    private String size;
    private short amountInStock;
    private String info;
    private Integer published;
}
