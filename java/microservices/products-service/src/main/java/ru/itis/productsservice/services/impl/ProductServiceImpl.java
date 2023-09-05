package ru.itis.productservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.productservice.dto.CustomPage;
import ru.itis.productservice.dto.ProductDto;
import ru.itis.productservice.entities.Product;
import ru.itis.productservice.repositories.ProductRepository;
import ru.itis.productservice.services.ProductService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Value("${pagination.default_page_size}")
    private int pageSize;

    @Override
    public CustomPage<ProductDto> getPaginatedProducts(Pageable pagination) {
        log.debug("Received request pagination: {}", pagination);
        pagination = Optional.ofNullable(pagination).orElse(PageRequest.of(0, pageSize));

        Page<Product> productsPage = productRepository.findAll(pagination);

        return getCustomPageFromSpringPage(productsPage);
    }

    @Override
    public CustomPage<ProductDto> getPaginatedProductsByName(Pageable pagination, String name) {
        log.debug("Received request by name <<{}>> pagination: {}", name, pagination);

        pagination = Optional.ofNullable(pagination).orElse(PageRequest.of(0, pageSize));

        Page<Product> productsPage = productRepository.findAllByNameStartsWith(name, pagination);

        return getCustomPageFromSpringPage(productsPage);
    }

    @Override
    public CustomPage<ProductDto> getPaginatedProductsByCategory(Pageable pagination, String category) {
        log.debug("Received request by category <<{}>> pagination: {}", category, pagination);

        pagination = Optional.ofNullable(pagination).orElse(PageRequest.of(0, pageSize));

        Page<Product> productsPage = productRepository.findAllByCategoryStartsWith(category, pagination);

        return getCustomPageFromSpringPage(productsPage);
    }

    @Override
    public CustomPage<ProductDto> getPaginatedProductsWithDiscount(Pageable pagination) {
        log.debug("Received request by discount. Page: {} ", pagination);
        pagination = Optional.ofNullable(pagination).orElse(PageRequest.of(0, pageSize));

        Page<Product> productsPage = productRepository.findAllByDiscount(pagination);

        return getCustomPageFromSpringPage(productsPage);
    }

    private CustomPage<ProductDto> getCustomPageFromSpringPage(Page<Product> productsPage){
        return CustomPage.<ProductDto>builder()
                .content(ProductDto.from(productsPage.getContent()))
                .totalPages(productsPage.getTotalPages())
                .currentPage(productsPage.getNumber())
                .build();
    }
}
