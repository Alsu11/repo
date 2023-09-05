package ru.itis.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Форма для отправки письма")
public class EmailDto {

    @Schema(description = "Кому")
    private String to;

    @Schema(description = "Тема")
    private String subject;

    @Schema(description = "Название шаблона")
    private String templateName;

    @Schema(description = "Данные")
    private Map<String, Object> data;
}
