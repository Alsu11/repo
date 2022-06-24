package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.validation.annotations.CorrectCarNumber;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Форма для описания машины")
public class EntryForm {

    @Schema(description = "Модель машины", example = "BMW")
    @NotBlank(message = "The model of must be filled in")
    private String model;

    @Schema(description = "Номер машины", example = "к123кк12")
    @NotBlank(message = "The car number of must be filled in")
    @CorrectCarNumber(carNumber = "carNumber")
    private String carNumber;

    @Schema(description = "Цвет машины", example = "Черный")
    private String color;

    @Schema(description = "Количесвто часов парковки машины", example = "3")
    private Integer amountOfHours;

    @Schema(description = "Идентификатор владельца", example = "1")
    private Long userId;

    @Schema(description = "Адрес парковки", example = "SG9")
    @NotBlank(message = "Enter the address of parking")
    private String address;
}
