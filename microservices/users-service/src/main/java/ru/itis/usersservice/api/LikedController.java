package ru.itis.usersservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.usersservice.dto.ProductCollectionDto;

import java.util.UUID;

@RequestMapping("/{user-id}/liked")
public interface LikedController {

    @Operation(summary = "Получение всех продуктов из избранного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список продуктов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProductCollectionDto.class)
                            )
                    })
            , @ApiResponse(responseCode = "490", description = "Продукты не найдены")
    })
    @GetMapping
    ResponseEntity<ProductCollectionDto> getAllProductsInLiked(@PathVariable("user-id") UUID userId);


    @Operation(summary = "Удаление продукта из избранного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно удален")
    })
    @DeleteMapping("/delete/{product-id}")
    void deleteProductFromLiked(@PathVariable("product-id") UUID productId, @PathVariable("user-id") UUID userId);

    @PutMapping("/put/{product-id}")
    void addProductToLiked(@PathVariable("product-id") UUID productId, @PathVariable("user-id") UUID userId);
}
