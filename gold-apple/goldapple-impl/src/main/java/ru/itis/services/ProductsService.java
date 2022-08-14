package ru.itis.services;

import ru.itis.dto.AddToBasketDto;
import ru.itis.dto.AddToLikedDto;
import ru.itis.dto.ProductDto;
import ru.itis.dto.ProductsPage;

import java.util.Set;

public interface ProductsService {
    void addProductInBasket(AddToBasketDto addToBasketDto);

    void addProductInLiked(AddToLikedDto addToLikedDto);

    ProductsPage searchProductByName(String productName, int page);

    Set<ProductDto> searchProductByCategory(String productCategory);

    Set<ProductDto> getProductsWithDiscount();
}
