package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для входа")
public class SignInForm {

    @Schema(description = "Почта", example = "yumadilova@gmail.com")
    @Email
    private String email;

    @Schema(description = "Пароль", example = "12345")
    @NotBlank
    private String password;
}

