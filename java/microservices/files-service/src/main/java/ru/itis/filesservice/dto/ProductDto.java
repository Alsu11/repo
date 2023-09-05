package ru.itis.filesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Продукт")
public class ProductDto {

    @Schema(description = "Название", example = "Сыр")
    private String name;

    @Schema(description = "Категория", example = "Молочная продукция")
    private String category;

    @Schema(name = "Марка", example = "Простоквашино")
    private String maker;

    @Schema(name = "Артикул", example = "RK-26880")
    private String vendorCode;

    @Schema(name = "Цена", example = "123")
    private Integer price;

    @Schema(name = "Описание", example = "кбжу: 12, 13, 14")
    private String description;

    @Schema(name = "Скидка", example = "12")
    private Integer discount;

    @Schema(name = "Идентификатор изображения продукта", example = "87da190a-8c09-4a9d-aedc-c77fdc13f4e1")
    private UUID picture;
}
