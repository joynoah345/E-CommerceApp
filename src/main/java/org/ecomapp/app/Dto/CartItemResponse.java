package org.ecomapp.app.Dto;

import lombok.Data;
import org.ecomapp.app.Entity.CartItem;
import org.ecomapp.app.Entity.Product;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long userId;
//    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private CartItemProduct product;
}
