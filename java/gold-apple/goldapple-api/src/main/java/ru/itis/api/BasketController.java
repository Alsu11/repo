package ru.itis.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.ProductCollectionDto;

import java.util.UUID;

@RequestMapping("/{user-id}/basket")
@Tag(name = "Корзина пользователя")
public interface BasketController {

    @Operation(summary = "Получение всех продуктов из корзины пользователя")
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
    ResponseEntity<ProductCollectionDto> getAllProductsInBasket(@PathVariable("user-id") UUID userId);


    @Operation(summary = "Удаление продукта из корзины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно удален")
    })
    @DeleteMapping("/delete/{product-id}")
    void deleteProductFromBasket(@PathVariable("product-id") UUID productId, @PathVariable("user-id") UUID userId);
}
