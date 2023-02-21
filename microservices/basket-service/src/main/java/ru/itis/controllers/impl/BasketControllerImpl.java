package ru.itis.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.controllers.BasketController;
import ru.itis.dto.ProductCollectionDto;
import ru.itis.services.BasketService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class BasketControllerImpl implements BasketController {

    private final BasketService basketService;

    @Override
    public ResponseEntity<ProductCollectionDto> getAllProductsInBasket(UUID userId) {
        return ResponseEntity.ok(basketService.getBasket(userId));
    }

    @Override
    public void deleteProductFromBasket(UUID productId, UUID userId) {
        basketService.deleteProduct(productId, userId);
    }

    @Override
    public void addProductInBasket(UUID id, UUID prodId) {
        basketService.addProduct(prodId, id);
    }
}
