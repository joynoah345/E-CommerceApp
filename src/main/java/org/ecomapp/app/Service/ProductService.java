package org.ecomapp.app.Service;

import lombok.RequiredArgsConstructor;
import org.ecomapp.app.Dto.ProductRequest;
import org.ecomapp.app.Dto.ProductResponse;
import org.ecomapp.app.Entity.Product;
import org.ecomapp.app.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapRequestToProduct(product,productRequest);
        return mapProductToResponse(productRepository.save(product));
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productsList = productRepository.findByIsActiveTrue();
        return productsList.stream()
                .map(this :: mapProductToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return mapProductToResponse(product);
    }

    public Optional<ProductResponse> updateProductByid(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElse(null);
//            product.setName(productRequest.getName());
//            product.setDescription(productRequest.getDescription());
//            product.setPrice(productRequest.getPrice());
//            product.setStockQuantity(productRequest.getStockQuantity());
//            product.setCategory(productRequest.getCategory());
//            product.setImageUrl(productRequest.getImageUrl());
        return productRepository.findById(id)
                .map(existingProduct -> {
                    mapRequestToProduct(existingProduct,productRequest);
                    productRepository.save(existingProduct);
                    return mapProductToResponse(existingProduct);
                });
//            productRepository.save(product);
//        return mapProductToResponse(product);
    }

    private ProductResponse mapProductToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setStockQuantity(product.getStockQuantity());
        response.setDescription(product.getDescription());
        return response;
    }

    private void mapRequestToProduct(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());

    }

    public Boolean removeProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setIsActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepository.searchProductByKeyword(keyword)
                .stream()
                .map(this::mapProductToResponse)
                .toList();
    }
}
