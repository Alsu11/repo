package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для регистрации")
public class SignUpForm {

    @Schema(description = "Имя", example = "Алсу")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;

    @Schema(description = "Фамилия", example = "Юмадилова")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastName;

    @Schema(description = "Почта", example = "yumadilova@gmail.com")
    @Email
    private String email;

    @Schema(description = "Пароль", example = "12345")
    @NotBlank
    @Size(min = 4, max = 20, message = "Пароль не подходит, минимальный размер - {min}, максимальный - {max}")
    private String password;
}
