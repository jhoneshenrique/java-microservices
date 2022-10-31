package com.jhsoftware.productservice.service;

import com.jhsoftware.productservice.Repository.ProductRepository;
import com.jhsoftware.productservice.dto.ProductRequest;
import com.jhsoftware.productservice.dto.ProductResponse;
import com.jhsoftware.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j //Add logs
public class ProductService {

    //Dependency Injection
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        //Mapping a ProductRequest into a Product
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        //Log of product created
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        //Map product class into ProductResponse
        return products.stream().map(this::mapToProductResponse).toList();
    }

    //Creates an object of ProductResponse and returns it
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
