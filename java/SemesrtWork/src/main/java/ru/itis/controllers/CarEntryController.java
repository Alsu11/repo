package ru.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.CarEntryDto;
import ru.itis.dto.LeaveDto;
import ru.itis.services.CarEntryService;

import javax.validation.Valid;

@Tag(name = "Машины", description = "Контроллер, связанный с машинами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/car-entry")
public class CarEntryController {

    private final CarEntryService carEntryService;

    @Operation(summary = "Получение информации о возможности выезда машины из парковки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Машина, если она может выехать",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = CarEntryDto.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/can-go")
    public CarEntryDto canGo(@Valid @RequestBody LeaveDto leave) {
        return carEntryService.canCarGoOut(leave);
    }
}
