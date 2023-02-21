package ru.itis.usersservice.dto;

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
    @Email
    @Schema(description = "Почта", example = "lsu@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Пароль", example = "100")
    private String password;
}
