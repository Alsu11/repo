package ru.itis.shelter.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.shelter.dto.AddCatToShelter;
import ru.itis.shelter.dto.CatDto;
import ru.itis.shelter.dto.CatsPage;
import ru.itis.shelter.dto.UpdateCatDto;

import javax.validation.Valid;

@RequestMapping("/cats")
public interface CatsController {

    @Operation(summary = "Добавление определенного кота в определенный приют")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Добавленный кот",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = CatDto.class)
                            )
                    }
            )
    })
    @PutMapping("/add-to-shelter")
    ResponseEntity<CatDto> addToShelter(@Valid @RequestBody AddCatToShelter addCatToShelter);


    @Operation(summary = "Получение всех котов с пагинацией и сортировкой") //чтоб сказать, что делает метод, о чем метод
    @ApiResponses(value = { // хранит все данные
            @ApiResponse(responseCode = "200", description = "Список котов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = CatsPage.class) // описание
                            )
                    }
            )
    })
    @GetMapping("/get-all")
    ResponseEntity<CatsPage> getAll(@RequestParam("page") int page);


    @Operation(summary = "Удаление кота")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кот удален")
    })
    @DeleteMapping("/go-on/{cat-id}")
    ResponseEntity goOn(@PathVariable("cat-id") Long catId);


    @Operation(summary = "Обновление определенного кота")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обновленный кот",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = CatDto.class)
                            )
                    }
            )
    })
    @PutMapping("/update")
    ResponseEntity<CatDto> updatePost(@Valid @RequestBody UpdateCatDto updateCatDto);

}
