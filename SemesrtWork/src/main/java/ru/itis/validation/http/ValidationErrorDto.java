package ru.itis.validation.http;

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
@Schema(description = "Поля или объекты с ошибкой")
public class ValidationErrorDto {

    @Schema(description = "Поле", example = "carNumber")
    private String field;

    @Schema(description = "Объект", example = "EntryForm")
    private String object;

    @Schema(description = "Сообщение", example = "Неккоректный номер машины")
    private String message;
}
