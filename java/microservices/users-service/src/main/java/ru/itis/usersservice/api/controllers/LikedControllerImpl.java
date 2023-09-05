package ru.itis.usersservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.usersservice.api.LikedController;
import ru.itis.usersservice.dto.ProductCollectionDto;
import ru.itis.usersservice.services.LikedService;

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
    public void deleteProductFromLiked(UUID productId, UUID userId) {
        likedService.deleteProduct(productId, userId);
    }

    @Override
    public void addProductToLiked(UUID productId, UUID userId) {
        likedService.addProduct(productId, userId);
    }
}
