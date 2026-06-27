package org.ecomapp.app.Repository;

import org.ecomapp.app.Dto.CartItemResponse;
import org.ecomapp.app.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("select c from cart_items c where c.user.id = :userId and c.product.id = :productId")
    CartItem findItemByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") Long productId);

    @Query("select c from cart_items c where c.user.id = :userId")
    List<CartItem> findByUserId(@Param("userId") Long userId);
}
