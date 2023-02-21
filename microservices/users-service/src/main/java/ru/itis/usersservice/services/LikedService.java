package ru.itis.usersservice.services;

import ru.itis.usersservice.dto.ProductCollectionDto;

import java.util.UUID;

public interface LikedService {
    ProductCollectionDto getAllProductsInLiked(UUID userId);

    void addProduct(UUID productId, UUID userId);

    void deleteProduct(UUID productId, UUID userId);

}
