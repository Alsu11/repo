package ru.itis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.validation.annotations.UuidFormat;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для добавления продукта в избранное")
public class AddToLikedDto {

    @Schema(description = "Идентификатор продукта", example = "87da190a-8c09-4a9d-aedc-c77fdc13f4e1")
    @NotNull(message = "Идентификатор продукта не может быть пустым")
    @UuidFormat(uuid = "productId")
    private UUID productId;

    @Schema(description = "Идентификатор пользователя", example = "87da190a-8c09-4a9d-aedc-c77fdc13f4e1")
    @NotNull(message = "Идентификатор пользователя не может быть пустым")
    @UuidFormat(uuid = "userId")
    private UUID userId;
}
