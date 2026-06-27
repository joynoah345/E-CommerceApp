package org.ecomapp.app.Repository;

import org.ecomapp.app.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActiveTrue();
    @Query("Select p from products p where p.isActive = true and p.stockQuantity > 0 and LOWER(p.name) like LOWER(Concat('%',:kw,'%'))")
    List<Product> searchProductByKeyword(@Param("kw") String keyword);
}
