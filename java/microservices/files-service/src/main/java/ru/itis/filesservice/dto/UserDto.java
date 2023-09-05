package ru.itis.filesservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Пользователь")
public class UserDto {

    @Schema(description = "Имя", example = "Яблоко")
    private String firstName;

    @Schema(description = "Фамилия", example = "Золотое")
    private String lastName;

    @Schema(description = "Почта", example = "goldApple@gmail.com")
    private String email;

    @Schema(description = "Номер телефона", example = "9962364578")
    private String phoneNumber;

    @Schema(description = "Персональная скидка", example = "10")
    private Integer discount;

    @Schema(description = "Идентификатор аватара", example = "87da190a-8c09-4a9d-aedc-c77fdc13f4e1")
    private UUID avatar;

    @Schema(description = "Рефреш токен", example = "87da190a-8c09-4a9d-aedc-c77fdc13f4e1")
    private String refreshToken;

    @Schema(description = "Аксес токен")
    private String accessToken;
}
