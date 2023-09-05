package ru.itis.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.usersservice.validation.annotations.CardFormat;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CardFormat(cvv = "cvv", expiration = "expiration", username = "userName")
@Schema(description = "Форма добавления карты")
public class AddCardDto {

    @Schema(description = "Номер карты", example = "123 4567 8912 3456")
    @NotBlank(message = "Номер карты не может быть пустым")
    private String number;

    @Schema(name = "Дата истечения", example = "22/22")
    @NotBlank(message = "Дата истечения карты не может быть пустой")
    private String expiration;

    @Schema(name = "Трёхзначный код на обороте карты", example = "123")
    @NotBlank(message = "Трехзначный код не может быть пустым")
    private String cvv;

    @Schema(name = "Имя владельца", example = "GOLD APPLE")
    @NotBlank(message = "Имя и Фамилия владельца карты не могут быть пустыми")
    private String userName;
}
