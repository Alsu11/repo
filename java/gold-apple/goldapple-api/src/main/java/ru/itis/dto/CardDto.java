package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Карта")
public class CardDto {
    @Schema(description = "Номер карты", example = "123 4567 8912 3456")
    private String number;

    @Schema(description = "Дата истечения", example = "22/22")
    private String expiration;

    @Schema(description = "Имя пользователя", example = "ALSU YUMADILOVA")
    private String userName;
}
