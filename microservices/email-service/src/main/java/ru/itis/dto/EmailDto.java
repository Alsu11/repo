package ru.itis.dto;

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

    @Schema(description = "Почта, куда отправлять", example = "user@mail.com")
    private String to;

    @Schema(description = "Тема письма", example = "Email confirmation")
    private String subject;

    @Schema(description = "Название шаблона Freemarker", example = "confirm_mail")
    private String templateName;

    @Schema(description = "Данные для шаблона")
    private Map<String, Object> data;
}
