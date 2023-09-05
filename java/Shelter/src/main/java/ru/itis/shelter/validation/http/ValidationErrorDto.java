package ru.itis.shelter.validation.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Поля и объекты с ошибкой")
public class ValidationErrorDto {

    @Schema(description = "Поле", example = "health")
    private String field;

    @Schema(description = "Объект", example = "UpdateCarDto")
    private String object;

    @Schema(description = "Сообщение", example = "Неккоректно введено состояние здоровья кота")
    private String message;
}
