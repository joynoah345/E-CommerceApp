package org.ecomapp.app.Controller;

import lombok.RequiredArgsConstructor;
import org.ecomapp.app.Dto.CartItemRequest;
import org.ecomapp.app.Dto.CartItemResponse;
import org.ecomapp.app.Dto.ProductRequest;
import org.ecomapp.app.Dto.ProductResponse;
import org.ecomapp.app.Service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Boolean> addToCart(@RequestHeader("User-Id") Long userId,
            @RequestBody CartItemRequest request) {
            Boolean created = cartItemService.addProductToCart(userId,request);
            return created ? ResponseEntity.status(HttpStatus.CREATED).build(): ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllCartItems(@RequestHeader("User-Id") Long userId){
        return ResponseEntity.ok(cartItemService.getAllItems(userId));
    }

    @PutMapping
    public ResponseEntity<Void> updateCartItem(@RequestHeader("User-Id") Long userId,
            @RequestBody CartItemRequest request) {
        Boolean updated = cartItemService.updateCartItem(userId,request);
        return updated ? ResponseEntity.ok().build(): ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("item/{id}")
    public ResponseEntity<Void> deleteCartItem(@RequestHeader("User-Id") Long userId,
                               @PathVariable Long id ){
        Boolean deleted = cartItemService.removeCartItem(userId,id);
        return deleted ? ResponseEntity.ok().build(): ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
