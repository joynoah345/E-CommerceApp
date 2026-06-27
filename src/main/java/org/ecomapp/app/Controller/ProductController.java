package org.ecomapp.app.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.ecomapp.app.Dto.ProductRequest;
import org.ecomapp.app.Dto.ProductResponse;
import org.ecomapp.app.Entity.Product;
import org.ecomapp.app.Repository.ProductRepository;
import org.ecomapp.app.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        return  new ResponseEntity<ProductResponse>(productService.addProduct(product),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
                return productService.updateProductByid(id,productRequest)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        return productService.removeProduct(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> deleteProductById(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }
}
