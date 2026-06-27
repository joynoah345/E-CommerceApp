package org.ecomapp.app.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemProduct {
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
}
