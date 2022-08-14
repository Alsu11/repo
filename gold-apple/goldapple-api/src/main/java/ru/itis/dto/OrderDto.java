package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Заказ")
public class OrderDto {
    @Schema(description = "Цена", example = "123")
    private Integer amount;

    @Schema(description = "Пользователь", example = "{\"firstName\" : \"Alsu\", ...}")
    private UserDto user;

    @Schema(description = "Аддресс магазина", example = "ул. Пархоменко, д. 10")
    private ShopAddressDto shopAddress;

    @Schema(description = "Набор продуктов", example = "{\"name\" : \"123\", ...}")
    private Set<ProductDto> products;

}
