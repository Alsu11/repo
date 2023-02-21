package ru.itis.usersservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Корзина")
public class ProductCollectionDto {

    @Schema(description = "Список продуктов", example = "{\"name\" : \"сыр\", ...}")
    private List<ProductDto> productsDto;
}
