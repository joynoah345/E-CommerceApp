package org.ecomapp.app.Service;


import lombok.Data;
import org.ecomapp.app.Dto.*;
import org.ecomapp.app.Entity.CartItem;
import org.ecomapp.app.Entity.Product;
import org.ecomapp.app.Entity.User;
import org.ecomapp.app.Repository.CartItemRepository;
import org.ecomapp.app.Repository.ProductRepository;
import org.ecomapp.app.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public Boolean addProductToCart(Long userId, CartItemRequest cartItemRequest) {
        Product product = productRepository.findById(cartItemRequest.getProductId()).get();
        User user = userRepository.findById(userId).get();
        if (user == null || product == null || product.getStockQuantity() < cartItemRequest.getQuantity()) {
            return false;
        }
        CartItem cartItem = cartItemRepository.findItemByUserIdAndProductId(userId,product.getId());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItem.setTotalPrice(cartItem.getPrice().add(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity()))));
            cartItemRepository.save(cartItem);
        }
        else{
            CartItem cartItem1 = new CartItem();
            cartItem1.setQuantity(cartItemRequest.getQuantity());
            cartItem1.setProduct(product);
            cartItem1.setUser(user);
            cartItem1.setPrice(product.getPrice());
            cartItem1.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepository.save(cartItem1);
        }
        return true;
    }

    public List<CartItemResponse> getAllItems(Long userId) {
        return cartItemRepository.findByUserId(userId)
                .stream()
                .map(item -> mapCartItemToResponse(item))
                .toList();
    }

    private CartItemResponse mapCartItemToResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setUserId(cartItem.getUser().getId());
//        response.setProductId(cartItem.getProduct().getId());
        response.setQuantity(cartItem.getQuantity());
        response.setTotalPrice(cartItem.getPrice());
        CartItemProduct cartItemProduct = new CartItemProduct();
        cartItemProduct.setId(cartItem.getProduct().getId());
        cartItemProduct.setName(cartItem.getProduct().getName());
        cartItemProduct.setPrice(cartItem.getProduct().getPrice());
        cartItemProduct.setDescription(cartItem.getProduct().getDescription());
        response.setProduct(cartItemProduct);
        return response;
    }

    public Boolean updateCartItem(Long userId,CartItemRequest request) {
        CartItem item = cartItemRepository.findItemByUserIdAndProductId(userId,request.getProductId());
        if  (item == null) {
            return false;
        }
        if(request.getQuantity() == 0){
            return removeCartItem(userId,request.getProductId());
        }
        Product product = productRepository.findById(request.getProductId()).get();
        item.setQuantity(request.getQuantity());
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        cartItemRepository.save(item);
        return true;
    }

    public Boolean removeCartItem(Long userId, Long productId) {
        CartItem cartItem = cartItemRepository.findItemByUserIdAndProductId(userId,productId);
        if  (cartItem == null) {
            return false;
        }
        cartItemRepository.delete(cartItem);
        return true;
    }
}
