package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Машина")
public class CarEntryDto {

    @Schema(description = "Модель машины", example = "BMW")
    private String model;

    @Schema(description = "Номер машины", example = "к123кк12")
    private String carNumber;

    @Schema(description = "Цвет машины", example = "Черный")
    private String color;

    @Schema(description = "Время начала парковки", example = "12015-11-21T11:01:32.610Z")
    private String startTime;

    @Schema(description = "Время конца парковки", example = "2015-11-21T11:01:32.610Z")
    private String endTime;

    @Schema(description = "Идентификатор владельца", example = "1")
    private Long userId;

    @Schema(description = "Адрес парковки", example = "SG9")
    private String address;

}
