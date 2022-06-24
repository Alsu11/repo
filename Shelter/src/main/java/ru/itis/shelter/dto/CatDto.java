package ru.itis.shelter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.shelter.models.CatEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Кот")
public class CatDto {
    @Schema(description = "Здоровье кота", example = "HEALTH")
    private String health;

    @Schema(description = "Место нахождение кота", example = "ON_SHELTER")
    private String state;

    @Schema(description = "Имя", example = "Фитюлек")
    private String name;

    @Schema(description = "Возраст", example = "4")
    private Long age;

    @Schema(description = "Адресс приюта", example = "Du")
    private String shelterAddress;


    public static CatDto from(CatEntity catEntity) {
        return CatDto.builder()
                .name(catEntity.getName())
                .age(catEntity.getAge())
                .health(catEntity.getHealth().toString())
                .state(catEntity.getState().toString())
                .shelterAddress(catEntity.getShelter().getAddress())
                .build();
    }

    public static List<CatDto> from(List<CatEntity> cats) {
        return cats.stream().map(CatDto::from).collect(Collectors.toList());
    }
}