package ru.itis.services;

import ru.itis.dto.ProductCollectionDto;

import java.util.UUID;

public interface BasketService {
    ProductCollectionDto getBasket(UUID userId);
    void addProduct(UUID productId, UUID userId);
    void deleteProduct(UUID productId, UUID userId);
}
