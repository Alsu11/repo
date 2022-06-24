package ru.itis.shelter.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.shelter.dto.ShelterDto;

import java.util.List;

@RequestMapping("/shelter")
public interface ShelterController {

    @Operation(summary = "Получение списка приютов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список приютов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ShelterDto.class)
                            )
                    }
            )
    })
    @GetMapping("/get-all")
    ResponseEntity<List<ShelterDto>> getAll();
}
