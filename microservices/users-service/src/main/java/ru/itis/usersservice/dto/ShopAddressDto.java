package ru.itis.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Адресс магазина")
public class ShopAddressDto {
    @Schema(description = "Адресс", example = "ул. Пархоменко, д. 10")
    private String address;
}
