package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.shelter.models.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Пользователь")
public class UserDto {

    @Schema(description = "Состояние", example = "CONFIRMED")
    private String state;

    @Schema(description = "Имя", example = "Алсу")
    private String firstName;

    @Schema(description = "Фамилия", example = "Юмадилова")
    private String lastName;

    @Schema(description = "Почта", example = "yumadilova@gmail.com")
    private String email;

    public static UserDto from(UserEntity user) {
        return UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .state(user.getState().toString())
                .build();
    }

    public static List<UserDto> from(List<UserEntity> list) {
        return list.stream().map(UserDto::from).collect(Collectors.toList());
    }
}
