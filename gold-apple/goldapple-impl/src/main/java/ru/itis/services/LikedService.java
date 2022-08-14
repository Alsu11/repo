package ru.itis.services;

import ru.itis.dto.ProductCollectionDto;

import java.util.UUID;

public interface LikedService {
    ProductCollectionDto getAllProductsInLiked(UUID userId);

    void addProduct(UUID productId, UUID userId);

    void deleteProduct(UUID productId, UUID userId);

}
