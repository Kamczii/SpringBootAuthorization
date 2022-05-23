package com.pawcie.authorization.products;

import com.pawcie.authorization.utilities.TypeProduct;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Getter
    @Setter
    private TypeProduct type;
    @Getter
    @Setter
    private char size;
    @Getter
    @Setter
    private short amountInStock;
    @Getter
    @Setter
    private String info;

}
