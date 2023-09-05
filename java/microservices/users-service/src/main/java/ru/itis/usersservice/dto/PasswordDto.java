package ru.itis.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.usersservice.validation.annotations.CustomPassword;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Форма для смены пароля")
public class PasswordDto {
    @Schema(description = "Почта", example = "goldApple@gmail.com")
    @Email
    private String email;

    @Schema(description = "Старый пароль")
    private String oldPassword;

    @Schema(description = "Новый пароль")
    @CustomPassword
    private String newPassword;
}
