package ru.itis.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.usersservice.validation.annotations.CustomPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Форма для регистрации")
public class SignUpForm {

    @Schema(description = "Имя", example = "Яблоко")
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 15, message = "First name minimum size {min}, maximum size {max}")
    private String firstName;

    @Schema(description = "Фамилия", example = "Золотое")
    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 2, max = 15, message = "last name minimum size {min}, maximum size {max}")
    private String lastName;

    @Schema(description = "Почта", example = "goldApple@gmail.com")
    @NotBlank(message = "Email cannot be empty")
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @Schema(description = "Номер телефона", example = "9962364578")
    @Size(min = 10, max = 10, message = "Phone number must contain {max} digits")
    private String phoneNumber;

    @Schema(description = "Пароль", example = "100")
    @NotBlank(message = "Password cannot be empty")
    @CustomPassword
    @Size(min = 8, max = 600, message = "Password minimum size {min}, maximum size {max}")
    private String password;

}
