package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для добавления кота в приют")
public class AddCatToShelter {

    @Schema(description = "Имя", example = "Фитюлек")
    @NotBlank
    private String name;

    @Schema(description = "Возраст", example = "4")
    private Long age;

    @Schema(description = "Идентификатор приюта", example = "1")
    private Long shelterId;
}
