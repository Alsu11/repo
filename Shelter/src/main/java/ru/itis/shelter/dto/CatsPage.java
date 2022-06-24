package ru.itis.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Список котов с пагинацией")
public class CatsPage {

    @Schema(description = "Коты на данной странице")
    List<CatDto> cats;

    @Schema(description = "Общее количество страниц", example = "2")
    private Integer totalPages;
}
