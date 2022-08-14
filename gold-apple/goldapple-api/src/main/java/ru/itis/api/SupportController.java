package ru.itis.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.SupportDto;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/support")
public interface SupportController {

    @Operation(summary = "Обращение в поддержку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение успешно отправлено!",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = SupportDto.class)
                            )
                    })
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
    })
    @PostMapping("/{user-id}")
    ResponseEntity<SupportDto> addComment(@PathVariable("user-id") UUID userId, @Valid @RequestBody SupportDto supportDto);

}
