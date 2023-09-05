package ru.itis.productservice.services;

import org.springframework.data.domain.Pageable;
import ru.itis.productservice.dto.CustomPage;
import ru.itis.productservice.dto.ProductDto;

public interface ProductService {
    CustomPage<ProductDto> getPaginatedProducts(Pageable pagination);

    CustomPage<ProductDto> getPaginatedProductsByName(Pageable pagination, String name);

    CustomPage<ProductDto> getPaginatedProductsByCategory(Pageable pagination, String category);

    CustomPage<ProductDto> getPaginatedProductsWithDiscount(Pageable pagination);
}
