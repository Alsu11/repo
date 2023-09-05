package ru.itis.validation.http;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Список полей и/или объектов с ошибками")
public class ValidationExceptionResponse {
    @ArraySchema(schema = @Schema(description = "Ошибки", implementation = ValidationErrorDto.class))
    private List<ValidationErrorDto> errors;
}
