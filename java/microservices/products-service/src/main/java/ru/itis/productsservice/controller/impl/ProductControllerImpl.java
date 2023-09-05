package ru.itis.productservice.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.productservice.controller.ProductController;
import ru.itis.productservice.dto.CustomPage;
import ru.itis.productservice.dto.ProductDto;
import ru.itis.productservice.services.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ResponseEntity<CustomPage<ProductDto>> getProducts(Pageable pagination) {
        return ResponseEntity.ok(productService.getPaginatedProducts(pagination));
    }

    @Override
    public ResponseEntity<CustomPage<ProductDto>> getProductsByName(Pageable pagination, String name) {
        return ResponseEntity.ok(productService.getPaginatedProductsByName(pagination, name));
    }

    @Override
    public ResponseEntity<CustomPage<ProductDto>> getProductsByCategory(Pageable pagination, String category) {
        return ResponseEntity.ok(productService.getPaginatedProductsByCategory(pagination, category));
    }
    @Override
    public ResponseEntity<CustomPage<ProductDto>> getProductsWithDiscount(Pageable pagination) {
        return ResponseEntity.ok(productService.getPaginatedProductsWithDiscount(pagination));
    }
}
