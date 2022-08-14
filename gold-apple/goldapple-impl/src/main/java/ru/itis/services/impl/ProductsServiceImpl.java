package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.ProductsRepository;
import ru.itis.dto.*;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.ProductsException;
import ru.itis.exceptions.UsersException;
import ru.itis.models.ProductEntity;
import ru.itis.services.BasketService;
import ru.itis.services.LikedService;
import ru.itis.services.ProductsService;
import ru.itis.utils.mappers.ProductsMapper;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductsServiceImpl implements ProductsService {

    private final BasketService basketService;
    private final LikedService likedService;
    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Value("${default-page-size}")
    private int defaultPageSize;

    @Transactional
    @Override
    public void addProductInBasket(AddToBasketDto addToBasketDto) {
        basketService.addProduct(addToBasketDto.getProductId(), addToBasketDto.getUserId());
    }

    @Transactional
    @Override
    public void addProductInLiked(AddToLikedDto addToLikedDto) {
        likedService.addProduct(addToLikedDto.getProductId(), addToLikedDto.getUserId());
    }

    @Transactional
    @Override
    public ProductsPage searchProductByName(String productName, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<ProductEntity> products = productsRepository.findAllByName(productName, pageRequest);

        return ProductsPage.builder()
                .products(productsMapper.toResponse(products.toSet()))
                .totalPages(products.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public Set<ProductDto> searchProductByCategory(String productCategory) {
        Set<ProductEntity> productsEntity= productsRepository.findAllByCategory(productCategory);

        return productsMapper.toResponse(productsEntity);
    }

    @Transactional
    @Override
    public Set<ProductDto> getProductsWithDiscount() {
        return productsMapper.toResponse(productsRepository.findAllWithDiscount());
    }
}
