package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description =  "Страницы с продуктами")
public class ProductsPage {
    @Schema(description = "Список продуктов", example = "{\"name\" : \"сыр\", ...}")
    private Set<ProductDto> products;

    @Schema(description = "Количество страниц", example = "12")
    private Integer totalPages;
}
