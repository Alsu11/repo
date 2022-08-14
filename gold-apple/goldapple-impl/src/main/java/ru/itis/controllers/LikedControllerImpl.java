package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.api.LikedController;
import ru.itis.dto.ProductCollectionDto;
import ru.itis.services.LikedService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class LikedControllerImpl implements LikedController {

    private final LikedService likedService;

    @Override
    public ResponseEntity<ProductCollectionDto> getAllProductsInLiked(UUID userId) {
        return ResponseEntity.ok(likedService.getAllProductsInLiked(userId));
    }

    @Override
    public void deleteProductFromBasket(UUID productId, UUID userId) {
        likedService.deleteProduct(productId, userId);
    }
}
