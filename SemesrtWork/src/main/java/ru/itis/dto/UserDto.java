package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Пользователь")
public class UserDto {

    @Schema(description = "Идентификатор пользователя")
    private Long id;

    @Schema(description = "Почта", example = "alsu.yumadilova@gmail.com")
    private String email;

    @Schema(description = "Имя", example = "Алсу")
    private String firstName;

    @Schema(description = "Фамилия", example = "Юмадилова")
    private String lastName;

    @Schema(description = "Количество денег у пользователя в рублях", example = "12")
    private Double money;
}
