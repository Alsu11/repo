package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Форма входа")
public class SignInForm {

    @Email
    @Schema(description = "Почта", example = "alsu.yumadilova@gmail.com")
    private String email;

    @Schema(description = "Пароль", example = "password")
    private String password;

}
