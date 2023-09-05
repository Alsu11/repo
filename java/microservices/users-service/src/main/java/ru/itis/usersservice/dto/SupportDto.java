package ru.itis.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Поддержка сайта")
public class SupportDto {

    @Schema(name = "Сообщение")
    @NotBlank(message = "Сообщение не может быть пустым")
    private String message;

    @Schema(name = "Дата отправления обращения в поддержку")
    private Instant date;
}
