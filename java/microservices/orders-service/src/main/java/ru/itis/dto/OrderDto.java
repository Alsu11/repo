package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Заказ")
public class OrderDto {

    private UUID id;

    @Schema(description = "Цена", example = "123")
    private Integer amount;

    @Schema(description = "Пользователь", example = "{\"firstName\" : \"Alsu\", ...}")
    private UserDto user;

    @Schema(description = "Аддресс магазина", example = "ул. Пархоменко, д. 10")
    private ShopAddressDto shopAddress;

    @Schema(description = "Набор продуктов", example = "{\"name\" : \"123\", ...}")
    private Set<ProductDto> products;

}
