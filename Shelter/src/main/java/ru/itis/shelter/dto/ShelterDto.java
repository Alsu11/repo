package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.shelter.models.ShelterEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Приют")
public class ShelterDto {

    @Schema(description = "Адресс приюта", example = "Du")
    private String address;

    @Schema(description = "Общее количество котов", example = "34")
    private Long numberOfCats;

    public static ShelterDto from(ShelterEntity shelterEntity) {
        return ShelterDto.builder()
                .address(shelterEntity.getAddress())
                .numberOfCats(shelterEntity.getNumberOfCats())
                .build();
    }

    public static List<ShelterDto> from(List<ShelterEntity> shelters) {
        return shelters.stream().map(ShelterDto::from).collect(Collectors.toList());
    }

}
