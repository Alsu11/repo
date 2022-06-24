package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Форма регистрации")
public class SignUpForm {

    @Email
    @Schema(description = "Почта", example = "alsu.yumadilova@gmail.com")
    private String email;

    @Size(min = 5, max = 15)
    @Schema(description = "Пароль", example = "password")
    private String password;

    @Schema(description = "Имя", example = "Алсу")
    private String firstName;

    @Schema(description = "Фамилия", example = "Юмадилова")
    private String lastName;
}
