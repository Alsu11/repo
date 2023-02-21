package ru.itis.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Унифицированная страница для пагинации")
public class CustomPage<T> {

    @Schema(description = "Общее количество страниц", example = "4")
    private int totalPages;
    @Schema(description = "Текущая страница с отсчетом от нуля", example = "0")
    private int currentPage;
    @Schema(description = "Контент страницы")
    private List<T> content;

}
