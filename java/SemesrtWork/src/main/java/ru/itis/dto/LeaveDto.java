package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.validation.annotations.CorrectCarNumber;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для ввода номера машины")
public class LeaveDto {

    @NotBlank(message = "The car number of must be filled in")
    @CorrectCarNumber(carNumber = "carNumber")
    @Schema(description = "Номер машины", example = "к123кк12")
    private String carNumber;

}
