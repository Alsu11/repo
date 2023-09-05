package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.shelter.models.CatEntity;
import ru.itis.shelter.validation.annotations.UpdateCatFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@UpdateCatFormat(state = "state", health = "health")
@Schema(description = "Форма для обновления кота")
public class UpdateCatDto {

    @Schema(description = "Здоровье кота", example = "HEALTH")
    private CatEntity.Health health;

    @Schema(description = "Место нахождение кота", example = "ON_SHELTER")
    private CatEntity.State state;

    @Schema(description = "Идентификатор кота", example = "1")
    @NotNull
    private Long catId;
}
