package ru.itis.usersservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.usersservice.dto.AddCardDto;

import javax.validation.Valid;
import java.util.UUID;

public interface CardsController {

    @Operation(summary = "Добавление карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о карте",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = String.class)
                            )
                    })
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
    })
    @PutMapping("/{user-id}/add-card")
    ResponseEntity<String> addCard(@PathVariable("user-id") UUID userId, @Valid @RequestBody AddCardDto addCardDto);

}
